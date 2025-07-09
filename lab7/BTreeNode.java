package btreelab;

public class BTreeNode {
    double[] keys;
    int minDegree;
    BTreeNode[] children;
    int numKeys;
    boolean isLeaf;

    public BTreeNode(int t, boolean leaf) {
        this.minDegree = t;
        this.isLeaf = leaf;
        this.keys = new double[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.numKeys = 0;
    }

    private int binarySearch(double key) {
        int low = 0;
        int high = numKeys - 1;
        int index = numKeys;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (keys[mid] == key) {
                return mid;
            } else if (keys[mid] < key) {
                low = mid + 1;
            } else {
                index = mid;
                high = mid - 1;
            }
        }
        return index;
    }

    public int findKey(double key) {
        int idx = binarySearch(key);
        if (idx < numKeys && keys[idx] == key) {
            return idx;
        }
        return idx;
    }

    public void insertNonFull(double key) {
        int i = numKeys - 1;
        if (isLeaf) {
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            numKeys++;
        } else {
            int idx = binarySearch(key);
            
            if (children[idx].numKeys == 2 * minDegree - 1) {
                splitChild(idx, children[idx]);
                if (keys[idx] < key) {
                    idx++;
                }
            }
            children[idx].insertNonFull(key);
        }
    }

    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.minDegree, y.isLeaf);
        z.numKeys = minDegree - 1;
        for (int j = 0; j < minDegree - 1; j++) {
            z.keys[j] = y.keys[j + minDegree];
        }
        if (!y.isLeaf) {
            for (int j = 0; j < minDegree; j++) {
                z.children[j] = y.children[j + minDegree];
            }
        }
        y.numKeys = minDegree - 1;
        for (int j = numKeys; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }
        children[i + 1] = z;
        for (int j = numKeys - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }
        keys[i] = y.keys[minDegree - 1];
        numKeys++;
    }

    public void remove(double k) {
        int idx = findKey(k);
        if (idx < numKeys && keys[idx] == k) {
            if (isLeaf) {
                removeFromLeaf(idx);
            } else {
                removeFromNonLeaf(idx);
            }
        } else {
            if (isLeaf) {
                return;
            }
            boolean flag = (idx == numKeys);
            if (children[idx].numKeys < minDegree) {
                fill(idx);
            }
            if (idx > numKeys) {
                children[idx - 1].remove(k);
            } else {
                children[idx].remove(k);
            }
        }
    }

    public void removeFromLeaf(int idx) {
        for (int i = idx + 1; i < numKeys; i++) {
            keys[i - 1] = keys[i];
        }
        numKeys--;
    }

    public void removeFromNonLeaf(int idx) {
        double k = keys[idx];
        if (children[idx].numKeys >= minDegree) {
            BTreeNode pred = getPred(idx);
            keys[idx] = pred.keys[pred.numKeys - 1];
            children[idx].remove(keys[idx]);
        } else if (children[idx + 1].numKeys >= minDegree) {
            BTreeNode succ = getSucc(idx);
            keys[idx] = succ.keys[0];
            children[idx + 1].remove(keys[idx]);
        } else {
            merge(idx);
            children[idx].remove(k);
        }
    }

    public BTreeNode getPred(int idx) {
        BTreeNode curr = children[idx];
        while (!curr.isLeaf) {
            curr = curr.children[curr.numKeys];
        }
        return curr;
    }

    public BTreeNode getSucc(int idx) {
        BTreeNode curr = children[idx + 1];
        while (!curr.isLeaf) {
            curr = curr.children[0];
        }
        return curr;
    }

    public void fill(int idx) {
        if (idx != 0 && !children[idx - 1].isLeaf && children[idx - 1].numKeys >= minDegree) {
            borrowFromPrev(idx);
        } else if (idx != numKeys && !children[idx + 1].isLeaf && children[idx + 1].numKeys >= minDegree) {
            borrowFromNext(idx);
        } else {
            if (idx != numKeys) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
        }
    }

    public void borrowFromPrev(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];
        for (int i = child.numKeys - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.isLeaf) {
            for (int i = child.numKeys; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }
        child.keys[0] = keys[idx - 1];
        if (!child.isLeaf) {
            child.children[0] = sibling.children[sibling.numKeys];
        }
        keys[idx - 1] = sibling.keys[sibling.numKeys - 1];
        child.numKeys += 1;
        sibling.numKeys -= 1;
    }

    public void borrowFromNext(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];
        child.keys[child.numKeys] = keys[idx];
        if (!child.isLeaf) {
            child.children[child.numKeys + 1] = sibling.children[0];
        }
        keys[idx] = sibling.keys[0];
        for (int i = 0; i < sibling.numKeys - 1; i++) {
            sibling.keys[i] = sibling.keys[i + 1];
        }
        if (!sibling.isLeaf) {
            for (int i = 0; i < sibling.numKeys; i++) {
                sibling.children[i] = sibling.children[i + 1];
            }
        }
        child.numKeys += 1;
        sibling.numKeys -= 1;
    }

    public void merge(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];
        child.keys[minDegree - 1] = keys[idx];
        for (int i = 0; i < sibling.numKeys; i++) {
            child.keys[i + minDegree] = sibling.keys[i];
        }
        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.numKeys; i++) {
                child.children[i + minDegree] = sibling.children[i];
            }
        }
        for (int i = idx + 1; i < numKeys; i++) {
            keys[i] = keys[i + 1];
        }
        for (int i = idx + 1; i <= numKeys; i++) {
            children[i] = children[i + 1];
        }
        child.numKeys += sibling.numKeys + 1;
        numKeys--;
    }

    public void traverse() {
        int i;
        for (i = 0; i < numKeys; i++) {
            if (!isLeaf) {
                children[i].traverse();
            }
            System.out.print(" " + keys[i]);
        }
        if (!isLeaf) {
            children[i].traverse();
        }
    }

    public BTreeNode search(double key) {
        int i = 0;
        i = binarySearch(key);

        if (i < numKeys && keys[i] == key) {
            return this;
        }

        if (isLeaf) {
            return null;
        }

        return children[i].search(key);
    }
}
package btreelab;

public class BTree {
    private BTreeNode root;
    private int t;

    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    public void traverse() {
        if (root != null) {
            root.traverse();
        }
        System.out.println();
    }

    public BTreeNode search(double key) {
        return (root == null) ? null : root.search(key);
    }

    public void insert(double key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.numKeys = 1;
        } else {
            if (root.numKeys == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
                s.children[0] = root;
                s.splitChild(0, root);
                int idx = 0;
                if (s.keys[0] < key) {
                    idx = 1;
                }
                s.children[idx].insertNonFull(key);
                root = s;
            } else {
                root.insertNonFull(key);
            }
        }
    }

    public void remove(double key) {
        if (root == null) {
            return;
        }
        root.remove(key);
        if (root.numKeys == 0) {
            if (root.isLeaf) {
                root = null;
            } else {
                root = root.children[0];
            }
        }
    }
}
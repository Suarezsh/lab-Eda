import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Node<T extends Comparable<T>> {
    T data;
    Node<T> left;
    Node<T> right;
    Node<T> parent;

    Node(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

class BST<T extends Comparable<T>> {
    Node<T> root;

    BST() {
        root = null;
    }

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
            return;
        }

        Node<T> current = root;
        Node<T> parent = null;

        while (current != null) {
            parent = current;
            if (data.compareTo(current.data) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        newNode.parent = parent;
        if (data.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public Node<T> search(T data) {
        Node<T> current = root;
        while (current != null) {
            if (data.compareTo(current.data) == 0) {
                return current;
            } else if (data.compareTo(current.data) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public Node<T> getMin() {
        Node<T> current = root;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }

    public Node<T> getMax() {
        Node<T> current = root;
        while (current != null && current.right != null) {
            current = current.right;
        }
        return current;
    }

    public Node<T> getParent(T data) {
        Node<T> current = root;
        Node<T> parent = null;
        while (current != null) {
            if (data.compareTo(current.data) == 0) {
                return parent;
            } else {
                parent = current;
                if (data.compareTo(current.data) < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
        }
        return null;
    }

    public List<Node<T>> getChildren(Node<T> node) {
        List<Node<T>> children = new ArrayList<>();
        if (node.left != null) children.add(node.left);
        if (node.right != null) children.add(node.right);
        return children;
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una palabra en mayúsculas: ");
        String palabra = scanner.next();

        if (palabra.isEmpty()) {
            System.out.println("La entrada no puede estar vacía.");
            return;
        }

        for (char c : palabra.toUpperCase().toCharArray()) {
            bst.insert((int) c);
        }

        System.out.println("Operaciones implementadas:");
        System.out.println("Buscar nodo con valor 65: " + (bst.search(65) != null ? "Encontrado" : "No encontrado"));
        System.out.println("Nodo mínimo: " + bst.getMin().data);
        System.out.println("Nodo máximo: " + bst.getMax().data);
        System.out.println("Padre del nodo con valor 65: " + (bst.getParent(65) != null ? bst.getParent(65).data : "No tiene padre"));
        System.out.println("Hijos del nodo con valor 65: " + bst.getChildren(bst.search(65)));
    }
}

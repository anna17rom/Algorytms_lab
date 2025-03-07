import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class BST {

    long keyComparisons = 0;
    long pointerChangesAndReads = 0;
    List<Integer> heightAfterOperations = new ArrayList<>();
    List<Long> perOpKeyComparisons = new ArrayList<>();
    List<Long> perOppointerChangesAndReads = new ArrayList<>();
    int n;

    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    BST(int n) {
        root = null;
        this.n = n;
    }

    void insert(int key) {
        long startKeyComparisons = keyComparisons;
        long startpointerChangesAndReads = pointerChangesAndReads;
        Node newNode = new Node(key);
        if (root == null) {
            root = newNode;
            if (n < 50) {
                System.out.println("insert " + key);
                printTree(root, "", true, "root");
                System.out.println("current height: " + this.height());
            }
            return;
        }

        Node parent = null;
        Node current = root;
        while (current != null) {
            parent = current;
            keyComparisons++;
            if (key < current.key) {
                current = current.left;
                pointerChangesAndReads++;
            } else {
                current = current.right;
                pointerChangesAndReads++;
            }
        }
        keyComparisons++;
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        pointerChangesAndReads++;
        perOpKeyComparisons.add(keyComparisons - startKeyComparisons);
        perOppointerChangesAndReads.add(pointerChangesAndReads - startpointerChangesAndReads);
        heightAfterOperations.add(height());
        if (n < 50) {
            System.out.println("insert " + key);
            printTree(root, "", true, "root");
            System.out.println("current height: " + this.height());
        }
    }

    void delete(int key) {
        long startKeyComparisons = keyComparisons;
        long startPointerChangesAndReads = pointerChangesAndReads;
        Node parent = null;
        Node current = root;
        boolean found = false;

        // Find the node to delete
        while (current != null) {
            keyComparisons++;
            if (key == current.key) {
                found = true;
                break;
            } else {
                parent = current;
                keyComparisons++;
                if (key < current.key) {
                    current = current.left;
                } else {
                    current = current.right;
                }
                pointerChangesAndReads++;
            }
        }

        if (!found) return;


        if (current.left == null || current.right == null) {
            Node replacement = (current.left != null) ? current.left : current.right;
            keyComparisons++;
            if (parent == null) {
                root = replacement;
            } else if (current == parent.left) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
            pointerChangesAndReads++;
        } else {
           
            Node successorParent = current;
            Node successor = current.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
                pointerChangesAndReads+=2;
            }
            keyComparisons++;
            if (successorParent != current) {
                successorParent.left = successor.right;
                successor.right = current.right;
                pointerChangesAndReads+=2;
            }
            successor.left = current.left;
            pointerChangesAndReads++;
            keyComparisons++;
            if (parent == null) {
                root = successor;
            } else if (current == parent.left) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            pointerChangesAndReads++;
        }

        if (n < 50) {
            System.out.println("delete " + key);
            printTree(root, "", true, "root");
            System.out.println("current height: " + this.height());
        }
        perOpKeyComparisons.add(keyComparisons - startKeyComparisons);
        perOppointerChangesAndReads.add(pointerChangesAndReads- startPointerChangesAndReads);
        heightAfterOperations.add(height());
    }

    int height() {
        return heightRec(root);
    }

    int heightRec(Node root) {
        if (root == null)
            return 0;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int height = 0;

        while (!queue.isEmpty()) {
            int nodeCount = queue.size();
            height++;

            while (nodeCount > 0) {
                Node currentNode = queue.poll();

                if (currentNode.left != null)
                    queue.add(currentNode.left);
                if (currentNode.right != null)
                    queue.add(currentNode.right);

                nodeCount--;
            }
        }

        return height;
    }

    void printTree(Node node, String prefix, boolean isTail, String dir) {
        if (node == null)
            return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.key + "(" + dir + ")");
        String newPrefix = prefix + (isTail ? "    " : "|   ");

        if (node.right != null || node.left != null) {
            if (node.right != null)
                printTree(node.right, newPrefix, node.left == null, "R");
            if (node.left != null)
                printTree(node.left, newPrefix, true, "L");
        }
    }

    void resetStatistics() {
        keyComparisons = 0;
        pointerChangesAndReads = 0;
        perOpKeyComparisons.clear();
        perOppointerChangesAndReads.clear();
        heightAfterOperations.clear();
    }

    void saveStatistics(PrintWriter writer) {
        writer.println("Total key comparisons: " + keyComparisons);
        writer.println("Total pointer changes: " + pointerChangesAndReads);
        writer.println("Average key comparisons per operation: " +
                perOpKeyComparisons.stream().mapToLong(Long::longValue).average().orElse(0));
        writer.println("Max key comparisons per operation: " +
                perOpKeyComparisons.stream().mapToLong(Long::longValue).max().orElse(0));
        writer.println("Average pointer changes and reads per operation: " +
                perOppointerChangesAndReads.stream().mapToLong(Long::longValue).average().orElse(0));
        writer.println("Max pointer changes and reads per operation: " +
                perOppointerChangesAndReads.stream().mapToLong(Long::longValue).max().orElse(0));
    }
    void saveHeight(PrintWriter writer){
        writer.println(heightAfterOperations.toString());
    }
}























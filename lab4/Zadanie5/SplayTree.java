import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SplayTree {
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

    SplayTree(int n) {
        root = null;
        this.n = n;
    }
    private Node leftRotate(Node x) {
        //A
        Node y = x.right;
        //Przypisujemy do prawego dziecka B to, co jest lewym dzieckiem A, czyli β
        x.right = y.left;
        //B staje się lewym dzieckiem A
        y.left = x;
        pointerChangesAndReads += 3;
        return y;
    }

    private Node rightRotate(Node x) {
        //B
        Node y = x.left;
        //Przypisujemy do lewego dziecka A to, co jest prawym dzieckiem B, czyli β
        x.left = y.right;
        //A staje się prawym dzieckiem B
        y.right = x;
        pointerChangesAndReads += 3;
        return y;
    }
   // Metoda splay, żeby zrobić node jako root
   private Node splay(Node root, int key) {
        keyComparisons++;
       if (root == null || root.key == key) {
           return root;
       }

       // Jeśli szukany klucz jest mniejszy niż klucz w korzeniu, szukamy w lewym poddrzewie
       keyComparisons++;
       if (root.key > key) {
           // Jeśli lewe poddrzewo jest puste, nie można wykonać splay, zwróć aktualny korzeń
           pointerChangesAndReads++;
           if (root.left == null) return root;

           // Zig-Zig (Left Left) - rotacja w przypadku gdy szukany klucz jest mniejszy od klucza lewego dziecka
           keyComparisons+=2;
           pointerChangesAndReads++;
           if (root.left.key > key) {
               // Rekursywnie wykonaj splay dla lewego lewego wnuka
               pointerChangesAndReads++;
               root.left.left = splay(root.left.left, key);
               // Po wykonaniu splay, przeprowadź prawą rotację
               pointerChangesAndReads++;
               root = rightRotate(root);
               System.out.println("Zig-zig");
           }
           // Zig-Zag (Left Right) - rotacja w przypadku gdy szukany klucz jest większy od klucza lewego dziecka
           else if (root.left.key < key) {
               // Rekursywnie wykonaj splay dla lewego prawego wnuka
               pointerChangesAndReads++;
               root.left.right = splay(root.left.right, key);
               // Jeśli lewe prawe dziecko istnieje, wykonaj dla niego lewą rotację
               if (root.left.right != null) {
                   pointerChangesAndReads++;
                   root.left = leftRotate(root.left);
               }
               System.out.println("Zig-zag");

           }

           // Wykonaj finalną prawą rotację jeśli lewe dziecko istnieje, inaczej zwróć aktualny korzeń
           return (root.left == null) ? root : rightRotate(root);
       } else { // Przypadek gdy szukany klucz jest większy od klucza w korzeniu, szukamy w prawym poddrzewie
           // Jeśli prawe poddrzewo jest puste, zwróć aktualny korzeń
           if (root.right == null) return root;

           // Zag-Zig (Right Left) - rotacja gdy szukany klucz jest mniejszy od klucza prawego dziecka
           keyComparisons+=2;
           if (root.right.key > key) {
               // Rekursywnie wykonaj splay dla prawego lewego wnuka
               pointerChangesAndReads++;
               root.right.left = splay(root.right.left, key);
               // Jeśli prawe lewe dziecko istnieje, wykonaj dla niego prawą rotację
               if (root.right.left != null) {
                   pointerChangesAndReads++;
                   root.right = rightRotate(root.right);
               }
               System.out.println("Zag-zig");
           }
           // Zag-Zag (Right Right) - rotacja gdy szukany klucz jest większy od klucza prawego dziecka
           else if (root.right.key < key) {
               // Rekursywnie wykonaj splay dla prawego prawego wnuka
               pointerChangesAndReads++;
               root.right.right = splay(root.right.right, key);
               // Po wykonaniu splay, przeprowadź lewą rotację
               pointerChangesAndReads++;
               root = leftRotate(root);
               System.out.println("Zag-zag");

           }

           // Wykonaj finalną lewą rotację jeśli prawe dziecko istnieje, inaczej zwróć aktualny korzeń
           return (root.right == null) ? root : leftRotate(root);
       }
   }

    private void splayKey(int key) {
        pointerChangesAndReads++;
        root = splay(root, key);
    }


    void insert(int key) {
        long startKeyComparisons = keyComparisons;
        long startpointerChangesAndReadsAndReads = pointerChangesAndReads;
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
        splayKey(key);
        pointerChangesAndReads++;
        perOpKeyComparisons.add(keyComparisons - startKeyComparisons);
        perOppointerChangesAndReads.add(pointerChangesAndReads - startpointerChangesAndReadsAndReads);
        heightAfterOperations.add(height());
        if (n < 50) {
            System.out.println("insert " + key);
            printTree(root, "", true, "root");
            System.out.println("current height: " + this.height());
        }


    }


    void delete(int key) {
        long startKeyComparisons = keyComparisons;
        long startpointerChangesAndReadsAndReads = pointerChangesAndReads;
        if (root == null) {
            return;
        }

        splayKey(key);

        if (root.key != key)
            return;

        // Klucz jest znaleziony i jest teraz w korzeniu
        if (root.left == null) {
            pointerChangesAndReads++;
            root = root.right;
        } else if (root.right == null) {
            pointerChangesAndReads++;
            root = root.left;
        } else {
            Node rightSubtree = root.right;
            root.right = null; // Odłącz prawe poddrzewo
            root = root.left;  // Ustaw lewe poddrzewo jako nowy korzeń
            splayKey(findMax(root)); // Przesuń maksymalny klucz lewego poddrzewa na korzeń
            root.right = rightSubtree; // Przyłącz oryginalne prawe poddrzewo
            pointerChangesAndReads+=3;

        }
        perOpKeyComparisons.add(keyComparisons - startKeyComparisons);
        perOppointerChangesAndReads.add(pointerChangesAndReads - startpointerChangesAndReadsAndReads);
        heightAfterOperations.add(height());
        if (n < 50) {
            System.out.println("delete " + key);
            printTree(root, "", true, "root");
            System.out.println("current height: " + this.height());
        }


    }

    int findMax(Node node) {
        while (node.right != null) {
            pointerChangesAndReads++;
            node = node.right;
        }
        return node.key;
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

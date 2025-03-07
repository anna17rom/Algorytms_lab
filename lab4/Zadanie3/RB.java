import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

class RB {

    enum Color {RED, BLACK}

    private Node NULL_NODE;

    class Node {
        int key;
        Node left, right, parent;
        Color color;

        Node(int item) {
            key = item;
            left = right = NULL_NODE;
            parent = null;
            color = Color.RED;  // New nodes are red
        }

        Node() {
        }
    }

    private Node root;
    private long keyComparisons = 0;
    private long pointerChangesAndReads = 0;
    List<Long> perOpKeyComparisons = new ArrayList<>();
    List<Long> perOppointerChangesAndReads = new ArrayList<>();
    List<Integer> heightAfterOperations = new ArrayList<>();
    private int n;

    RB(int n) {
        root = null;
        this.n = n;
        NULL_NODE = new Node();
        NULL_NODE.color = Color.BLACK;
        NULL_NODE.left = null;
        NULL_NODE.right = null;
        root = NULL_NODE;
    }

    // Insertion and fix-up logic
    void insert(int key) {
        long startKeyComparisons = keyComparisons;
        long startpointerChangesAndReads = pointerChangesAndReads;
        Node newNode = new Node(key);
        //Rodzic nowego węzła
        Node y = null;
        //Przeszukuje drzewo w poszukiwaniu odpowiedniego miejsca na wstawienie
        Node x = this.root;
        pointerChangesAndReads++;

        while (x != NULL_NODE) {
            y = x;
            keyComparisons++;
            pointerChangesAndReads++;
            if (newNode.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        newNode.parent = y;
        pointerChangesAndReads++;
        //Jeżeli nie ma rodzica znaczy że to jest korzeń
        keyComparisons++;
        pointerChangesAndReads++;
        if (y == null) {
            root = newNode;
        } else if (newNode.key < y.key) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }
        newNode.color = Color.RED;
        fixInsert(newNode);
        perOpKeyComparisons.add(keyComparisons - startKeyComparisons);
        perOppointerChangesAndReads.add(pointerChangesAndReads - startpointerChangesAndReads);
        heightAfterOperations.add(height());
        if (n < 50) {
            System.out.println("insert " + key);
            printTree(root, "", true, "root");
            System.out.println("current height: " + this.height());
        }
    }

    //fix-up
    private void fixInsert(Node k) {
        // zmienna na wujka (uncle) węzła k
        Node u;
        // Kontynuuj dopóki rodzic węzła k jest czerwony (nie może być dwóch czerwonych węzłów z rzędu)
        while (k.parent != null && k.parent.color == Color.RED) {
            // Sprawdź, czy rodzic węzła k jest prawym dzieckiem dziadka
            keyComparisons++;
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // wujek jest lewym dzieckiem dziadka
                pointerChangesAndReads++;

                // Przypadek 1: Wujek węzła k jest czerwony, i mamy dwa czerwone węzły pod rząd
                if (u != NULL_NODE && u.color == Color.RED) {
                    // Rozwiązanie: Przekoloruj rodzica i wujka na czarno, a dziadka na czerwono
                    k.parent.color = Color.BLACK;
                    u.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    // Kontynuuj naprawę od dziadka, który teraz jest czerwony
                    k = k.parent.parent;
                    pointerChangesAndReads += 3;
                } else {
                    // Wujek jest czarny
                    keyComparisons++;
                    if (k == k.parent.left) {
                        // Przypadek 2: k jest lewym dzieckiem swojego rodzica, 2 czerwone pod rząd, zig-zag
                        // Rozwiązanie: Wykonaj rotację w prawo na rodzicu k
                        pointerChangesAndReads++;
                        k = k.parent;
                        rightRotate(k);
                    }
                    // Przypadek 3: k jest prawym dzieckiem swojego rodzica (po prawej rotacji, jeśli była wykonywana)
                    // Rozwiązanie: Zamień kolory rodzica k i dziadka, następnie wykonaj rotację w lewo na dziadku
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    pointerChangesAndReads += 2;
                    leftRotate(k.parent.parent);
                }
            } else {
                // Lustrzany przypadek: rodzic k jest lewym dzieckiem dziadka
                u = k.parent.parent.right; // wujek jest prawym dzieckiem dziadka
                pointerChangesAndReads++;

                if (u != NULL_NODE && u.color == Color.RED) {
                    // Lustrzany przypadek 1: Wujek węzła k jest czerwony
                    k.parent.color = Color.BLACK;
                    u.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                    pointerChangesAndReads += 3;
                } else {
                    keyComparisons++;
                    if (k == k.parent.right) {
                        // Lustrzany przypadek 2: k jest prawym dzieckiem
                        pointerChangesAndReads++;
                        k = k.parent;
                        leftRotate(k);
                    }
                    // Lustrzany przypadek 3: k jest lewym dzieckiem (po lewej rotacji, jeśli była wykonywana)
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    pointerChangesAndReads += 2;
                    rightRotate(k.parent.parent);
                }
            }
            // Jeżeli k jest korzeniem, przerwij pętlę
            keyComparisons++;
            if (k == root) {
                break;
            }
        }
        // Ustaw kolor korzenia na czarny
        root.color = Color.BLACK;
    }


    // Rotations
    private void leftRotate(Node x) {
        //A
        Node y = x.right;
        pointerChangesAndReads++;
        //Przypisujemy do prawego dziecka B to, co jest lewym dzieckiem A, czyli β
        x.right = y.left;
        pointerChangesAndReads++;
        //Ustawiamy rodzica β jako B, jeżeli istnieje β
        if (y.left != NULL_NODE) {
            y.left.parent = x;
            pointerChangesAndReads++;
        }
        //Rodzicem A staje się obecny rodzic B
        y.parent = x.parent;
        pointerChangesAndReads++;
        keyComparisons++;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
            pointerChangesAndReads++;

        } else {
            x.parent.right = y;
            pointerChangesAndReads++;

        }
        //B staje się lewym dzieckiem A
        y.left = x;
        //Rodzicem B staje się A
        x.parent = y;
        pointerChangesAndReads += 2;
    }

    private void rightRotate(Node x) {
        //B
        Node y = x.left;
        pointerChangesAndReads++;
        //Przypisujemy do lewego dziecka A to, co jest prawym dzieckiem B, czyli β
        x.left = y.right;
        pointerChangesAndReads++;
        //Ustawiamy rodzica β jako A, jeżeli istnieje β
        if (y.right != NULL_NODE) {
            y.right.parent = x;
            pointerChangesAndReads++;
        }
        //Rodzicem B staje się obecny rodzic A
        y.parent = x.parent;
        pointerChangesAndReads++;
        keyComparisons++;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
            pointerChangesAndReads++;
        } else {
            x.parent.left = y;
            pointerChangesAndReads++;
        }
        //A staje się prawym dzieckiem B
        y.right = x;
        //Rodzicem A staje się B
        x.parent = y;
        pointerChangesAndReads += 2;
    }

    // Usuwanie elementów
    void delete(int key) {
        long startKeyComparisons = keyComparisons;
        long startpointerChangesAndReads = pointerChangesAndReads;
        // Wyszukiwanie węzła o danym kluczu.
        Node node = search(root, key);
        if (node == NULL_NODE) return; // Element nie został znaleziony

        Node x;
        // y to węzeł, który zostanie usunięty lub przesunięty.
        Node y = node;
        Color originalColor = y.color;

        // Jeśli lewy potomek węzła jest null, użyj prawego potomka.
        if (node.left == NULL_NODE) {
            x = node.right;
            pointerChangesAndReads++;
            // Zastąp węzeł node jego prawym potomkiem.
            transplantSubtree(node, node.right);
            //na odwrót
        } else if (node.right == NULL_NODE) {
            x = node.left;
            pointerChangesAndReads++;
            transplantSubtree(node, node.left);

            // Jeśli węzeł posiada oba potomki.
        } else {
            // Znajdź najmniejszy węzeł w prawym poddrzewie.
            y = minimum(node.right);
            originalColor = y.color;
            // x to prawy potomek y, który wejdzie na miejsce y.
            x = y.right;
            pointerChangesAndReads++;
            //Jeśli y nie jest bezpośrednio obok node, musimy przeprowadzić transplantację,
            keyComparisons++;
            if (y.parent != node) {
                // Zastąp y jego prawym potomkiem.
                transplantSubtree(y, y.right);
                pointerChangesAndReads++;
                y.right = node.right;
                if (y.right != null) {
                    pointerChangesAndReads++;
                    y.right.parent = y;
                }
            } else {
                if (x != null) {
                    pointerChangesAndReads++;
                    x.parent = y;
                }
            }
            transplantSubtree(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.color = node.color;
            pointerChangesAndReads += 3;
        }
        if (originalColor == Color.BLACK) {
            if (x != null) {
                fixDelete(x);
            }
        }
        perOpKeyComparisons.add(keyComparisons - startKeyComparisons);
        perOppointerChangesAndReads.add(pointerChangesAndReads - startpointerChangesAndReads);
        heightAfterOperations.add(height());
        if (n < 50) {
            System.out.println("delete " + key);
            printTree(root, "", true, "root");
            System.out.println("current height: " + this.height());
        }
    }

    private Node search(Node node, int key) {
        while (node != NULL_NODE && key != node.key) {
            keyComparisons++;
            pointerChangesAndReads++;
            node = (key < node.key) ? node.left : node.right;
        }
        return node;
    }

    private void transplantSubtree(Node A, Node B) {
        keyComparisons++;
        if (A.parent == null) {
            pointerChangesAndReads++;
            root = B;
        } else if (A == A.parent.left) {
            pointerChangesAndReads++;
            A.parent.left = B;
        } else {
            pointerChangesAndReads++;
            A.parent.right = B;
        }
        if (B != null) {
            pointerChangesAndReads++;
            B.parent = A.parent;
        }
    }

    private void fixDelete(Node x) {
        // Kontynuj, dopóki x nie jest korzeniem i x jest czarny
        while (x != root && getColor(x) == Color.BLACK) {
            keyComparisons++;
            // Jeśli x jest lewym dzieckiem
            pointerChangesAndReads++;
            keyComparisons++;
            if (x == x.parent.left) {
                // w to brat węzła x
                Node w = x.parent.right;
                pointerChangesAndReads++;
                // Przypadek 1: x ma czerwonego brata po prawej stronie
                if (getColor(w) == Color.RED) {
                    // Kolor brata zmień na czarny, a rodzica x na czerwony
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    pointerChangesAndReads++;
                    leftRotate(x.parent);
                    w = x.parent.right;
                    pointerChangesAndReads++;
                }
                // Przypadek 2: Jeśli oba dzieci brata są czarne
                pointerChangesAndReads += 2;
                if (getColor(w.left) == Color.BLACK && getColor(w.right) == Color.BLACK) {
                    w.color = Color.RED;
                    pointerChangesAndReads++;
                    x = x.parent;
                    // Pzrypadek 3: jedno dziecko czarne, drugie jest czerwone
                } else {
                    pointerChangesAndReads++;
                    if (getColor(w.right) == Color.BLACK) {
                        w.left.color = Color.BLACK;
                        pointerChangesAndReads++;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = x.parent.right;
                        pointerChangesAndReads++;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    pointerChangesAndReads++;
                    w.right.color = Color.BLACK;
                    pointerChangesAndReads++;
                    leftRotate(x.parent);
                    x = root;
                    pointerChangesAndReads++;
                }
            } else {
                // Jeżeli x jest prawym dzieckiem
                //Lustrzany przypadek 1: brat jest po lewej stronie
                Node w = x.parent.left;
                pointerChangesAndReads++;
                if (getColor(w) == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    pointerChangesAndReads++;
                    rightRotate(x.parent);
                    w = x.parent.left;
                    pointerChangesAndReads++;
                }
                //Lustrzany przypadek 2: taki samy
                pointerChangesAndReads += 2;
                if (getColor(w.left) == Color.BLACK && getColor(w.right) == Color.BLACK) {
                    w.color = Color.RED;
                    pointerChangesAndReads++;
                    x = x.parent;
                } else {
                    pointerChangesAndReads++;
                    if (getColor(w.left) == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        pointerChangesAndReads++;
                        leftRotate(w);
                        w = x.parent.left;
                        pointerChangesAndReads++;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    pointerChangesAndReads++;
                    w.left.color = Color.BLACK;
                    pointerChangesAndReads++;
                    rightRotate(x.parent);
                    x = root;
                    pointerChangesAndReads++;
                }
            }
        }
        if (x != null) x.color = Color.BLACK;
    }


    private Node minimum(Node node) {
        while (node.left != NULL_NODE) {
            pointerChangesAndReads++;
            node = node.left;
        }
        return node;
    }

    private Color getColor(Node node) {
        if (node == null) return Color.BLACK;
        return node.color;
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

                if (currentNode.left != NULL_NODE && currentNode.left != null)
                    queue.add(currentNode.left);
                if (currentNode.right != NULL_NODE && currentNode.right != null)
                    queue.add(currentNode.right);

                nodeCount--;
            }
        }

        return height;
    }


    void printTree(Node node, String prefix, boolean isTail, String dir) {
        if (node == NULL_NODE) {
            return;
        }
        String color = (node.color == Color.RED) ? "RED" : "BLACK";
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.key + "(" + dir + ", " + color + ")");
        String newPrefix = prefix + (isTail ? "    " : "|   ");
        if (node.right != NULL_NODE || node.left != NULL_NODE) {
            if (node.right != NULL_NODE)
                printTree(node.right, newPrefix, node.left == null, "R");
            if (node.left != NULL_NODE)
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

    void saveHeight(PrintWriter writer) {
        writer.println(heightAfterOperations.toString());
    }
}


































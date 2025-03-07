import java.util.*;

public class Zadanie5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
       SplayTree tree = new SplayTree(n);

        // Przypadek 1: Wstawienie rosnącego ciągu
        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            keys.add(scanner.nextInt());
        }
        for (int key : keys) {
            tree.insert(key);
        }
        Collections.shuffle(keys);
        for (int key : keys) {
            tree.delete(key);
        }

        // Przypadek 2: Wstawienie losowego ciągu
        tree = new SplayTree(n);  // Reset drzewa
        Collections.shuffle(keys);
        for (int key : keys) {
            tree.insert(key);
        }
        Collections.shuffle(keys);
        for (int key : keys) {
            tree.delete(key);
        }
    }
}

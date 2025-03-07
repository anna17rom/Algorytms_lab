
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Zadanie6 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            try (PrintWriter writer1 = new PrintWriter("zadanie6.txt");
                 PrintWriter writer2 = new PrintWriter("zadanie6_height.txt")) {
                while ((line = reader.readLine()) != null) {
                    int n = Integer.parseInt(line);
                    List<Integer> keys = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        keys.add(Integer.parseInt(reader.readLine()));
                    }
                    SplayTree tree = new SplayTree(n);

                    // Przypadek 1: Wstawienie rosnącego ciągu i usuwanie losowego ciągu
                    for (int key : keys) {
                        tree.insert(key);
                    }
                    Collections.shuffle(keys);
                    for (int key : keys) {
                        tree.delete(key);
                    }
                    writer1.println("N = " + n + " (Rosnący ciąg -> Losowe usuwanie):");
                    tree.saveStatistics(writer1);
                    writer2.println("N = " + n + " (Rosnący ciąg -> Losowe usuwanie):");
                    tree.saveHeight(writer2);

                    // Reset statystyk
                    tree.resetStatistics();

                    // Przypadek 2: Wstawienie i usuwanie losowego ciągu
                    Collections.shuffle(keys);

                    for (int key : keys) {
                        tree.insert(key);
                    }
                    Collections.shuffle(keys);
                    for (int key : keys) {
                        tree.delete(key);
                    }
                    writer1.println("N = " + n + " (Losowy ciąg -> Losowe usuwanie):");
                    tree.saveStatistics(writer1);
                    writer2.println("N = " + n + " (Losowy ciąg -> Losowe usuwanie):");
                    tree.saveHeight(writer2);

                    writer1.println();
                    writer2.println();

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuickSort {
    private static int comparisons = 0;
    private static int swaps = 0;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int n = Integer.parseInt(line);
                int[] array = new int[n];
                int[] copy = new int[n];
                for (int i = 0; i < n; i++) {
                    array[i] = Integer.parseInt(reader.readLine());
                    copy[i] = array[i];
                }
                if (n < 40) {
                    printArray("Initial array:", array);
                }

                resetCounters();
                long startTime = System.currentTimeMillis();
                quickSort(array, 0, n - 1);  // Using a split size of 5 for `SELECT`
                long endTime = System.currentTimeMillis();

                recordData(comparisons, swaps, n, endTime - startTime);
                if (n < 40) {
                    printArray("Initial array:", copy);
                    printArray("Sorted array:", array);
                }

                System.out.println("Comparisons: " + comparisons);
                System.out.println("Swaps: " + swaps);
                if (isSortedSuccessfully(array))
                    System.out.println("Array sorted correctly");
                else System.out.println("Error i n array's order");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void quickSort(int[] array, int first, int last) {
        if (first < last) {
            int pi = partition(array, first, last);
            quickSort(array, first, pi - 1); // Pi - 1, bo pi jest już na właściwym miejscu
            quickSort(array, pi, last); // Zaczynamy od pi, a nie pi + 1
        }
    }

    private static int partition(int[] array, int first, int last) {
        int pivot = array[last]; // Używamy ostatniego elementu jako pivota dla uproszczenia
        int i = first - 1; // Indeks mniejszego elementu

        for (int j = first; j < last; j++) {
            // Jeśli aktualny element jest mniejszy lub równy pivotowi
            if (compare(array[j], pivot) <= 0) {
                i++;

                // swap arr[i] i arr[j]
                swap(array, i, j);
            }
        }

        // swap arr[i+1] i arr[last] (czyli pivot)
        swap(array, i + 1, last);

        return i + 1; // Zwracamy indeks pivota po partycjonowaniu
    }



    private static int compare(int a, int b) {
        comparisons++;
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        }
        return 0; // Dodane dla obsługi przypadku, gdy a == b
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        swaps++;
    }

    private static void printArray(String message, int[] array) {
        System.out.println(message);
        for (int j : array) {
            System.out.printf("%02d ", j);
        }
        System.out.println();
    }

    public static void recordData(int comparisons, int swaps, int n,long time) {
        try (FileWriter writer = new FileWriter("Data_QUICKSORT_Classic.txt", true)) {
            writer.write(n + " " + time + " " + comparisons + " " + swaps + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetCounters() {
        comparisons = 0;
        swaps = 0;
    }
    public static boolean isSortedSuccessfully(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class RandomizedSelect {

    private static Random rand = new Random();
    private static int comparisons = 0;
    private static int swaps = 0;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                int n = Integer.parseInt(line);  // Read n
                int k = Integer.parseInt(reader.readLine());  // Read k
                int[] array = new int[n];
                for (int i = 0; i < n; i++) {
                    array[i] = Integer.parseInt(reader.readLine());  // Read each array element
                }

                int[] originalArray = Arrays.copyOf(array, n);
                resetCounters();
                int kthStatistic = randomizedSelect(array, 0, n - 1, k);
                recordData(comparisons, swaps, n, k);
                if (n < 50) {

                    printArray("Końcowy stan tablicy: ", array);
                    printArray("Początkowy stan tablicy: ", originalArray);
                }
                Arrays.sort(originalArray);
                if (n < 50) {
                    System.out.println("Znaleziona statystyka pozycyjna: " + kthStatistic);
                    printArray("Posortowany ciąg kluczy: ", originalArray);
                }
                if (isCorrect(originalArray, kthStatistic, k)) {
                    System.out.println("POPRAWNIE");
                    System.out.println("Liczba porównań: " + comparisons);
                    System.out.println("Liczba przestawień: " + swaps);
                } else {
                    System.out.println("BŁĄD");
                }
                resetCounters();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int randomizedSelect(int[] array, int start, int end, int k) {
        if (start == end) {
            return array[start];
        }
        int pivotGlobalIndex = randomizedPartition(array, start, end);
        int length = pivotGlobalIndex - start + 1;
        if (k == length) {
            return array[pivotGlobalIndex];
        } else if (k < length) {
            return randomizedSelect(array, start, pivotGlobalIndex - 1, k);
        } else {
            return randomizedSelect(array, pivotGlobalIndex + 1, end, k - length);
        }
    }


    private static int randomizedPartition(int[] array, int start, int end) {
        int pivotIndex = start + rand.nextInt(end - start + 1);
        swap(array, pivotIndex, end);
        return partition(array, start, end, array.length);
    }


    private static int partition(int[] array, int start, int end, int n) {
        int pivot = array[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (compare(array[j], pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
       
        swap(array, i + 1, end);
        if (n < 50) {
            System.out.println("Pivot:" + pivot);
            printArray("Po partition: ", array);
        }
        return i + 1;
    }

    private static int compare(int a, int b) {
        comparisons++;
        return Integer.compare(a, b);
    }

    private static void swap(int[] array, int i, int j) {
        swaps++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void printArray(String message, int[] array) {
        System.out.println(message);
        for (int value : array) {
            System.out.printf("%02d ", value);
        }
        System.out.println();
    }

    private static boolean isCorrect(int[] array, int kthStatistic, int k) {
        return array[k - 1] == kthStatistic;
    }

    public static void resetCounters() {
        comparisons = 0;
        swaps = 0;
    }

    public static int getComparisons() {
        return comparisons;
    }

    public static int getSwaps() {
        return swaps;
    }

    private static void recordData(int comparisons, int swaps, int n, int k) {
        try {
            FileWriter InsertionSort = new FileWriter("Data_RANDOMIZEDSELECT.txt", true);
            InsertionSort.write(n + " " + k + " " + comparisons + " " + swaps + "\n");
            InsertionSort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





































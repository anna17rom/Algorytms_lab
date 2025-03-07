import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DualPivotQuickSort {
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
                dualPivotQuickSort(array, 0, n - 1);  // Using a split size of 5 for `SELECT`
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


    private static void dualPivotQuickSort(int[] array, int first, int last) {
        if (first < last) {
            int[] pivots = partition(array, first, last);
            int pivot1 = pivots[0];
            int pivot2 = pivots[1];
            dualPivotQuickSort(array, first, pivot1 - 1);
            dualPivotQuickSort(array, pivot1 + 1, pivot2 - 1);
            dualPivotQuickSort(array, pivot2 + 1, last);
        }
    }

    private static int[] partition(int[] array, int first, int last) {
        if (compare(array[first], array[last]) > 0) {
            swap(array, first, last);
        }
        int pivot1 = array[first], pivot2 = array[last];

        // Pointers
        int less = first + 1;
        int great = last - 1;

        for (int k = less; k <= great; k++) {
            if (compare(array[k], pivot1) < 0) {
                swap(array, k, less++);
            } else if (compare(array[k], pivot2) > 0) {
                while (k < great && compare(array[great], pivot2) > 0) {
                    great--;
                }
                swap(array, k, great--);

                if (compare(array[k], pivot1) < 0) {
                    swap(array, k, less++);
                }
            }
        }

        swap(array, first, --less);
        swap(array, last, ++great);

        return new int[]{less, great};
    }



    private static int compare(int a, int b) {
        comparisons++;
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        }
        return 0; 
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
        try (FileWriter writer = new FileWriter("Data_DUALPIVOTQUICKSORT_Classic.txt", true)) {
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


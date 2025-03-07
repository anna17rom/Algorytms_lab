

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DualPivotQuickSort1 {
    private static int comparisons = 0;
    private static int swaps = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] array = new int[n];
        int[] copy = new int[array.length];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
            copy[i] = array[i];
        }


        if (n < 40) {
            printArray("Initial array:", array);
        }

        dualPivotQuickSort(array, 0, n - 1, n);

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


    private static void dualPivotQuickSort(int[] array, int first, int last, int n) {
        if (first < last) {
            int[] pivots = partition(array, first, last);
            int pivot1 = pivots[0];
            int pivot2 = pivots[1];
            if (n < 40) {
                printArray("Pivot1: " + array[pivot1] + " index: " + pivot1 + " and Pivot2: " + array[pivot2] + " index: " + pivot2, array);
            }
            dualPivotQuickSort(array, first, pivot1 - 1, n);
            dualPivotQuickSort(array, pivot1 + 1, pivot2 - 1, n);
            dualPivotQuickSort(array, pivot2 + 1, last, n);
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
        // Swap pivots to final position
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
    public static boolean isSortedSuccessfully(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

}

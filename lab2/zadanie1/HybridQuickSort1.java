

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HybridQuickSort1 {
    private static int comparisons = 0;
    private static int swaps = 0;
    private static final int THRESHOLD = 20;


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

        quickSort(array, 0, n - 1, n);


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


    private static void quickSort(int[] array, int first, int last, int n) {
        if (first < last) {
            if (last - first < THRESHOLD) {
                insertionSort(array, first, last, n);
            } else {
                int pi = partition(array, first, last, n);
                quickSort(array, first, pi - 1, n);
                quickSort(array, pi + 1, last, n);
            }
        }
    }

    private static int partition(int[] array, int first, int last, int n) {
        int pivot = array[last];
        int i = first - 1;

        for (int j = first; j < last; j++) {
            if (compare(array[j], pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, last);
        if (n < 40) {
            printArray("(zadanie1.QuickSort) Pivot: " + pivot + " index: " + (i + 1), array);
        }
        return i + 1;
    }

    private static void insertionSort(int[] array, int first, int last, int n) {
        for (int i = first + 1; i <= last; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= first && compare(array[j], key) > 0) {
                swap(array, j, j + 1);
                j--;
            }
            array[j + 1] = key;
            if (n < 40) {
                printArray("(zadanie1.InsertionSort) The state of array after checking key " + key + ":", array);
            }
        }
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

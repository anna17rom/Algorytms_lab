
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MergeSort {
    private static int comparisons = 0;
    private static int swaps = 0;


    public static void main(String[] args) {
        int[] k = {1, 10, 100};
        List<Integer> ns = new ArrayList<>();
        for (int i = 10; i <= 50; i = i + 10) {
            ns.add(i);
        }
        for (int i = 1000; i <= 50000; i = i + 1000) {
            ns.add(i);
        }
        Scanner scanner = new Scanner(System.in);
        for (int n : ns) {
            for (int k_param : k) {
            for (int j = 0; j < k_param; j++) {

                    int[] array = new int[n];
                    int[] copy = new int[array.length];
                    for (int i = 0; i < n; i++) {
                        array[i] = scanner.nextInt();
                        copy[i] = array[i];
                    }


                    resetCounters();
                    mergeSort(array, 0, array.length - 1, n);
                    recordData(comparisons, swaps, n, k_param);


                    System.out.println("Comparisons: " + comparisons);
                if (isSortedSuccessfully(array))
                    System.out.println("Array sorted correctly");
                else System.out.println("Error i n array's order");
                }
            }
        }
    }


    public static void mergeSort(int[] arr, int left, int right, int n) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid, n);
            mergeSort(arr, mid + 1, right, n);
            merge(arr, left, mid, right, n);

        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int n) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0;

        int k = left;
        while (i < n1 && j < n2) {
            if (compare(L[i], R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }

    }

    private static void printArray(String message, int[] array, int start, int end) {
        System.out.print(message + " ");
        for (int i = start; i < end; i++) {
            System.out.printf("%02d ", array[i]);
        }
        System.out.println();
    }

    // Overloaded printArray method for initial and final array printouts
    private static void printArray(String message, int[] array) {
        printArray(message, array, 0, array.length);
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

    private static void recordData(int comparisons, int swaps, int n, int k) {
        try {
            FileWriter MergeSort = new FileWriter("Data_" + k + ".txt", true); // Append mode
            MergeSort.write("MergeSort:" + n + " " + comparisons + swaps + "\n");
            MergeSort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetCounters() {
        comparisons = 0;

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
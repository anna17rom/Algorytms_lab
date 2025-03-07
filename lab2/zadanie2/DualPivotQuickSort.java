import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DualPivotQuickSort {
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


                    if (n < 40) {
                    printArray("Initial array:", array);
                    }
                    resetCounters();
                    dualPivotQuickSort(array, 0, n - 1);
                    recordData(comparisons, swaps, n, k_param);

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

            }
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

    private static void recordData(int comparisons, int swaps, int n, int k) {
        try {
            FileWriter DualPivotQuickSort = new FileWriter("Data_" + k + ".txt", true); // Append mode
            DualPivotQuickSort.write("DualPivotQuickSort:" + n + " " + comparisons + " " + swaps + "\n");
            DualPivotQuickSort.close();
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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HybridQuickSort {
    private static int comparisons = 0;
    private static int swaps = 0;
    private static final int THRESHOLD = 10; // Określamy próg, poniżej którego używamy Insertion Sort

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
                    quickSort(array, 0, n - 1);
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


    private static void quickSort(int[] array, int first, int last) {
        if (first < last) {
            if (last - first < THRESHOLD) {
                insertionSort(array, first, last);
            } else {
                int pi = partition(array, first, last);
                quickSort(array, first, pi - 1);
                quickSort(array, pi + 1, last);
            }
        }
    }

    private static int partition(int[] array, int first, int last) {
        int pivot = array[last];
        int i = first - 1;

        for (int j = first; j < last; j++) {
            if (compare(array[j], pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, last);
        return i + 1;
    }

    private static void insertionSort(int[] array, int first, int last) {
        for (int i = first + 1; i <= last; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= first && compare(array[j], key) > 0) {
                swap(array, j, j + 1);
                j--;
            }
            array[j + 1] = key;
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

    private static void recordData(int comparisons, int swaps, int n, int k) {
        try {
            FileWriter HybridQuickSort = new FileWriter("Data_" + k + ".txt", true); // Append mode
            HybridQuickSort.write("HybridSort:" + n + " " + comparisons + " " + swaps + "\n");
            HybridQuickSort.close();
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
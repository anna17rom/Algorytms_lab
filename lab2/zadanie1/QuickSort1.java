

import java.util.Scanner;

public class QuickSort1 {
    private static int comparisons = 0;
    private static int swaps = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        if (n < 40) {
            printArray("Initial array:", array);
        }

        quickSort(array, 0, n - 1,n);

        if (n < 40) {
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
            int pi = partition(array, first, last);
            if(n<40){
                printArray("Pivot: " + array[pi] + " index: " + pi, array);}
            quickSort(array, first, pi - 1, n); // Pi - 1, bo pi jest już na właściwym miejscu
            quickSort(array, pi, last, n); // Zaczynamy od pi, a nie pi + 1
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
        return Integer.compare(a, b);
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        swaps++;
    }

    private static void printArray(String message, int[] array) {
        System.out.println(message);
        for (int value : array) {
            System.out.printf("%02d ", value);
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


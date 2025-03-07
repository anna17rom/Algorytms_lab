

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class InsertionSort1 {
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
        insertionSort(array,n);
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


    private static void insertionSort(int[] array, int n) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            for (int j = i; j > 0 && compare(array[j - 1], array[j]) == 1; j--) {
                swap(array, j, j - 1);
            }
            if (n < 40) {
                printArray("The state of array after checking key " + key + ":", array);
            }
        }
    }


    //Metoda porównuje elementy tablicy  i zwiększa licznik comparisons
    private static int compare(int a, int b) {
        comparisons++;
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        }
        return 0; // Dodane dla obsługi przypadku, gdy a == b
    }

    // Metoda zamienia mejscami elementy tablicy i  zwiększa licznik swaps
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        swaps++;
    }

    //Metoda dla wypisywania tablicy z formatem dwucyfrowym
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

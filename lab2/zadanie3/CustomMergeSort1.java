
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomMergeSort1 {
    private static int comparisons = 0;

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

        customMergeSort(array, n);

        if (n < 40) {
            printArray("Initial array:", copy);
            printArray("Sorted array:", array);
        }

        System.out.println("Comparisons: " + comparisons);
        if (isSortedSuccessfully(array))
            System.out.println("Array sorted correctly");
        else System.out.println("Error i n array's order");
    }

    public static void customMergeSort(int[] arr, int n) {
        List<int[]> subsequences = findIncreasingSubsequences(arr);
        if (subsequences.size() <= 1) {
            return; // Array is already in increasing order
        }
        if (n < 40) {
            System.out.println("Increasing Subsequences:");
            for (int[] subsequence : subsequences) {
                printArray("", subsequence);
            }
        }
        int[] sortedArray = mergeSortSubsequences(subsequences, 0, subsequences.size() - 1, n);
        System.arraycopy(sortedArray, 0, arr, 0, arr.length);
    }

    private static List<int[]> findIncreasingSubsequences(int[] arr) {
        List<int[]> subsequences = new ArrayList<>();
        int start = 0;
        for (int i = 1; i <= arr.length; i++) {
            if (i == arr.length || compare(arr[i - 1], arr[i]) > 0) {
                subsequences.add(java.util.Arrays.copyOfRange(arr, start, i));
                start = i;
            }
        }
        return subsequences;
    }

    private static int[] mergeSortSubsequences(List<int[]> subsequences, int left, int right, int n) {
        if (left == right) {
            return subsequences.get(left);
        }

        int mid = (left + right) / 2;
        int[] leftArray = mergeSortSubsequences(subsequences, left, mid, n);
        int[] rightArray = mergeSortSubsequences(subsequences, mid + 1, right, n);
        int[] merged = merge(leftArray, rightArray);

        if (n < 40) {
            System.out.println("Merging two arrays: ");
            printArray("array 1:", leftArray);
            printArray("array 2:", rightArray);
            printArray("After merging arrays", merged);
        }
        return merged;
    }

    private static int[] merge(int[] leftArray, int[] rightArray) {
        int[] result = new int[leftArray.length + rightArray.length];
        int i = 0, j = 0, k = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (compare(leftArray[i], rightArray[j]) <= 0) {
                result[k] = leftArray[i];
                k++;
                i++;
            } else {
                result[k] = rightArray[j];
                k++;
                j++;
            }
        }

        while (i < leftArray.length) {
            result[k] = leftArray[i];
            k++;
            i++;
        }

        while (j < rightArray.length) {
            result[k] = rightArray[j];
            k++;
            j++;
        }

        return result;
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


    private static void printArray(String message, int[] array) {
        System.out.println(message);
        for (int j : array) {
            System.out.printf("%02d ", j);
        }
        System.out.println();
    }

    // Metoda do sprawdzania, czy tablica jest posortowana
    public static boolean isSortedSuccessfully(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }


}

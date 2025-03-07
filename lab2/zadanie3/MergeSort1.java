
import java.util.Scanner;

public class MergeSort1 {
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

        mergeSort(array, 0, array.length - 1,n);

        if (n < 40) {
            printArray("Sorted array:", array);
        }

        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        if (isSortedSuccessfully(array))
            System.out.println("Array sorted correctly");
        else System.out.println("Error i n array's order");
    }

    public static void mergeSort(int[] arr, int left, int right,int n) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid,n);
            mergeSort(arr, mid + 1, right,n);
                if (n < 40) {
                    printArray("Merging two parts of array's fragment: ",arr, left, right+1);
                    printArray("part 1:", arr, left, mid + 1);
                    printArray("part 2:", arr, mid + 1, right + 1);
                }


            merge(arr, left, mid, right,n);
            if (n < 40) {
                printArray("The fragment after merging", arr, left, right+1);
            }

        }
    }

    private static void merge(int[] arr, int left, int mid, int right,int n) {
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
    public static boolean isSortedSuccessfully(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

}

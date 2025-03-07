
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomMergeSort {
    private static int swaps = 0;
    private static int comparisons = 0;

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
                    customMergeSort(array);
                    recordData(comparisons,swaps, n, k_param);

                    if (n < 40) {
                        printArray("Initial array:", copy);
                        printArray("Sorted array:", array);
                    }

                    System.out.println("Comparisons: " + comparisons);
                    System.out.println("Comparisons: " + comparisons);
                    if (isSortedSuccessfully(array))
                        System.out.println("Array sorted correctly");
                    else System.out.println("Error i n array's order");
                }
            }
        }
    }

    public static void customMergeSort(int[] arr) {
        List<int[]> subsequences = findIncreasingSubsequences(arr);
        if (subsequences.size() <= 1) {
            return; // Array is already in increasing order
        }

        int[] sortedArray = mergeSortSubsequences(subsequences, 0, subsequences.size() - 1);
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

    private static int[] mergeSortSubsequences(List<int[]> subsequences, int first, int last) {
        if (first == last) {
            return subsequences.get(first);
        }

        int mid = (first + last) / 2;
        int[] firstArray = mergeSortSubsequences(subsequences, first, mid);
        int[] lastArray = mergeSortSubsequences(subsequences, mid + 1, last);
        return merge(firstArray, lastArray);
    }

    private static int[] merge(int[] firstArray, int[] lastArray) {
        int[] result = new int[firstArray.length + lastArray.length];
        int i = 0, j = 0, k = 0;

        while (i < firstArray.length && j < lastArray.length) {
            if (compare(firstArray[i], lastArray[j]) <= 0) {
                result[k] = firstArray[i];
                k++;
                i++;
            } else {
                result[k] = lastArray[j];
                k++;
                j++;
            }
        }

        while (i < firstArray.length) {
            result[k] = firstArray[i];
            k++;
            i++;
        }

        while (j < lastArray.length) {
            result[k] = lastArray[j];
            k++;
            j++;
        }

        return result;
    }
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        swaps++;
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

    private static void recordData(int comparisons, int swaps, int n, int k) {
        try {
            FileWriter CustomSort = new FileWriter("Data_" + k + ".txt", true); // Append mode
            CustomSort.write("CustomSort:" + n + " " + comparisons + swaps+  "\n");
            CustomSort.close();
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

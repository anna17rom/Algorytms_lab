import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;

public class DualPivotQuicksortSelect {

    private static int comparisons = 0;
    private static int swaps = 0;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int n = Integer.parseInt(line);
                int[] array = new int[n];
                int[] copy = new int[n];
                for (int i = 0; i < n; i++) {
                    array[i] = Integer.parseInt(reader.readLine());
                    copy[i] = array[i];
                }
                if (n < 40) {
                    printArray("Initial array:", array);
                }

                resetCounters();
                long startTime = System.currentTimeMillis();
                customSort(array, n);  // Using a split size of 5 for `SELECT`
                long endTime = System.currentTimeMillis();

                recordData(comparisons, swaps, n, endTime - startTime);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void customSort(int[] arr,int n) {
        if (arr == null || arr.length <= 1)
            return;

        int left = 0;
        int right = arr.length - 1;

        // Sorting the entire array using custom quicksort
        customQuickSort(arr, left, right,n);
    }

    private static void customQuickSort(int[] arr, int left, int right,int n) {
        if (left < right) {
            // Choosing pivot elements using median of medians
            int[] pivots = findPivots(arr, left, right,n);
            int pivot1 = pivots[0];
            int pivot2 = pivots[1];

            // Partitioning the array around the pivot elements
            int[] pivot_indx = partition(arr, left, right, pivot1, pivot2);

            // Recursively sorting the subarrays
            customQuickSort(arr, left, pivot_indx[0] - 1,n);
            customQuickSort(arr, pivot_indx[0] + 1, pivot_indx[1] - 1,n);
            customQuickSort(arr, pivot_indx[1] + 1, right,n);
        }
    }

    private static int[] findPivots(int[] arr, int left, int right,int n) {
        // Choosing pivots using Median of Medians
        int pivot1 = select(arr, left, right, (right - left) / 3,5,n);
        int pivot2 = select(arr, left, right, 2 * (right - left) / 3,5,n);

        return new int[]{pivot1, pivot2};
    }
    public static int select(int[] array, int start, int end, int k, int splitNum, int n) {
        if (start == end) return array[start];


        int pivot = pivot(array, start, end, splitNum, n);
        int pivotIndex = partitionForPiv(array, start, end, pivot,n);
        int length = pivotIndex - start + 1;
        if (k == length) {
            return array[pivotIndex];
        } else if (k < length) {
            return select(array, start, pivotIndex - 1, k, splitNum, n);
        } else {
            return select(array, pivotIndex + 1, end, k - length, splitNum, n);
        }
    }

    private static int pivot(int[] arr, int start, int end, int splitNum, int n) {
        if (end - start + splitNum <= 0) return arr[start];
        ArrayList<Integer> medians = new ArrayList<>();
        for (int i = start; i <= end; i += splitNum) {
            int subEnd = Math.min(i + splitNum - 1, end);
            int median = findMedian(Arrays.copyOfRange(arr, i, subEnd + 1));
            if (n < 50) {
                printArray("Grupa: ", Arrays.copyOfRange(arr, i, subEnd + 1));
                System.out.println("Miediana tej grupy:" + median);
            }
            medians.add(median);
        }
        if (medians.size() == 1) return medians.get(0);
        int[] mediansArray = toArray(medians);
        return select(mediansArray, 0, mediansArray.length - 1, (mediansArray.length - 1) / 2, splitNum, n);
    }
    private static int findMedian(int[] subArray) {
        insertionSort(subArray);
        return subArray[subArray.length / 2];
    }

    private static int partitionForPiv(int[] arr, int start, int end, int pivot,int n) {
        int pivotIndex = -1;
        for (int i = start; i <= end; i++) {
            if (arr[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }
        swap(arr, pivotIndex, end); // Move pivot to end
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (compare(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }


        swap(arr, i + 1, end); // Move pivot to its final place
        if (n < 50) {
            System.out.println("Pivot:"+pivot);
            printArray("Po partition: ", arr);
        }
        return i + 1; // Return the position of the pivot
    }

    private static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            for (int j = i; j > 0 && compare(array[j - 1], array[j]) == 1; j--) {
                swap(array, j, j - 1);
            }
        }
    }

    private static int[] partition(int[] arr, int left, int right, int pivot1, int pivot2) {
        // Partitioning the array around the pivot elements pivot1 and pivot2
        int i = left;
        int j = left;
        int k = right;

        while (j <= k) {
            if (compare(arr[j],pivot1)==-1) {
                swap(arr, i, j);
                i++;
                j++;
            } else if (compare(arr[j],pivot1)<=0 && compare(arr[j],pivot2)<=0) {
                j++;
            } else {
                swap(arr, j, k);
                k--;
            }
        }
        return new int[]{i, k};}



    public static void recordData(int comparisons, int swaps, int n,long time) {
        try (FileWriter writer = new FileWriter("Data_DUALPIVOTQUICKSORT.txt", true)) {
            writer.write(n + " " + time + " " + comparisons + " " + swaps + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetCounters() {
        comparisons = 0;
        swaps = 0;
    }

    public static void swap(int[] array, int i, int j) {
        swaps++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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

    public static void printArray(String message, int[] array) {
        System.out.print(message);
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    private static int[] toArray(ArrayList<Integer> list) {
        return list.stream().mapToInt(i -> i).toArray();
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
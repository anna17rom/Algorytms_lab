import java.io.*;
import java.util.*;

public class QuicksortSelect {

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

                int[] originalArray = Arrays.copyOf(array, n);
                resetCounters();

                long startTime = System.currentTimeMillis();
                quicksort(array, 0, n - 1, 5);  // Using a split size of 5 for `SELECT`
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

    public static void quicksort(int[] array, int start, int end, int splitNum) {
        if (start < end) {
            int pivotIndex = select(array, start, end, (end - start + 1) / 2, splitNum);
            pivotIndex = partition(array, start, end, pivotIndex);


            quicksort(array, start, pivotIndex - 1, splitNum);
            quicksort(array, pivotIndex + 1, end, splitNum);
        }
    }

    public static int select(int[] array, int start, int end, int k, int splitNum) {
        if (start == end) return array[start];

        int pivot = pivot(array, start, end, splitNum);

        int pivotIndex = partition(array, start, end, pivot);
        int length = pivotIndex - start + 1;

        if (k == length) {
            return array[pivotIndex];
        } else if (k < length) {
            return select(array, start, pivotIndex - 1, k, splitNum);
        } else {
            return select(array, pivotIndex + 1, end, k - length, splitNum);
        }
    }

    public static int pivot(int[] arr, int start, int end, int splitNum) {
        if (end - start + 1 <= splitNum) return findMedian(Arrays.copyOfRange(arr, start, end + 1));

        ArrayList<Integer> medians = new ArrayList<>();
        for (int i = start; i <= end; i += splitNum) {
            int subEnd = Math.min(i + splitNum - 1, end);
            int median = findMedian(Arrays.copyOfRange(arr, i, subEnd + 1));
            medians.add(median);
        }

        int[] mediansArray = medians.stream().mapToInt(i -> i).toArray();
        return select(mediansArray, 0, mediansArray.length - 1, (mediansArray.length - 1) / 2, splitNum);
    }

    public static int findMedian(int[] subArray) {
        insertionSort(subArray);
        return subArray[subArray.length / 2];
    }

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            for (int j = i; j > 0 && compare(array[j - 1], array[j]) == 1; j--) {
                swap(array, j, j - 1);
            }
        }
    }

    public static int partition(int[] arr, int start, int end, int pivot) {
        int pivotIndex = -1;
        for (int i = start; i <= end; i++) {
            if (compare(arr[i],pivot)==0) {
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
        return i + 1; // Return the position of the pivot
    }

    public static void recordData(int comparisons, int swaps, int n,long time) {
        try (FileWriter writer = new FileWriter("Data_QUICKSORT_Select.txt", true)) {
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

    public static int compare(int a, int b) {
        comparisons++;
        return Integer.compare(a, b);
    }

    public static void printArray(String message, int[] array) {
        System.out.print(message);
        for (int value : array) {
            System.out.print(value + " ");
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
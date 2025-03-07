import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;


public class Select3 {

    private static int comparisons = 0;
    private static int swaps = 0;
    private static int[] splitNums = new int[]{3, 5, 7, 9};

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {


                int n = Integer.parseInt(line);  // Read n
                int k = Integer.parseInt(reader.readLine());  // Read k
                int[] array = new int[n];
                for (int i = 0; i < n; i++) {
                    array[i] = Integer.parseInt(reader.readLine());  // Read each array element
                }
                for (int splitNum : splitNums) {

                    int[] originalArray = Arrays.copyOf(array, n);
                    resetCounters();
                    long startTime = System.currentTimeMillis();
                    int kthStatistic = select(array, 0, n - 1, k, splitNum, n);
                    long endTime = System.currentTimeMillis();
                    recordData(comparisons, swaps, n, k, splitNum,endTime - startTime);
                    if (n < 50) {
                        printArray("Końcowy stan tablicy: ", array);
                        printArray("Początkowy stan tablicy: ", originalArray);
                    }
                    Arrays.sort(originalArray);
                    if (n < 50) {
                        System.out.println("Znaleziona statystyka pozycyjna: " + kthStatistic);
                        printArray("Posortowany ciąg kluczy: ", originalArray);
                    }
                    if (isCorrect(originalArray, kthStatistic, k)) {
                        System.out.println("POPRAWNIE");
                        System.out.println("Liczba porównań: " + comparisons);
                        System.out.println("Liczba przestawień: " + swaps);
                    } else {
                        System.out.println("BŁĄD");
                    }
                    resetCounters();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int select(int[] array, int start, int end, int k, int splitNum, int n) {
        if (start == end) return array[start];


        int pivot = pivot(array, start, end, splitNum, n);
        int pivotIndex = partition(array, start, end, pivot, n);
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

    private static int partition(int[] arr, int start, int end, int pivot, int n) {
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
            System.out.println("Pivot:" + pivot);
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


    private static int compare(int a, int b) {
        comparisons++;
        return Integer.compare(a, b);
    }

    private static void swap(int[] array, int i, int j) {
        swaps++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void printArray(String message, int[] array) {
        System.out.print(message);
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    private static boolean isCorrect(int[] array, int kthStatistic, int k) {
        return array[k - 1] == kthStatistic;
    }

    public static void resetCounters() {
        comparisons = 0;
        swaps = 0;
    }

    private static void recordData(int comparisons, int swaps, int n, int k, int splitNum, long time) {
        try (FileWriter writer = new FileWriter("Data_SELECT_zadanie3.txt", true)) {
            writer.write(n + " " + k + " " + splitNum + " " + comparisons + " " + swaps +" "+time+ "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] toArray(ArrayList<Integer> list) {
        return list.stream().mapToInt(i -> i).toArray();
    }
}


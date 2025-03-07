import java.util.Arrays;
import java.util.Random;
import java.io.*;

public class DataGenerator3 {
    private static final int MAX_N = 100000; // Max number of elements
    private static final int STEP = 1000;    // Step size for each experiment
    private static final int M = 50;         // Number of repetitions for averaging

    public static void main(String[] args) throws IOException {
        Random rand = new Random();

        for (int n = 1000; n <= MAX_N; n += STEP) {
            for (int i = 0; i < M; i++) {
                int[] array = generateAscendingArray(n);
                // Values to search for: close to start, middle, end, and non-existent
                int valueAtStart = array[Math.min(10, n - 1)];  // Closer to start but always within bounds
                int valueAtMiddle = array[n / 2];               // Middle element
                int valueAtEnd = array[Math.max(0, n - 10)];    // Closer to end but always within bounds
                int nonExistentValue = array[0] - 1;        // Assumed non-existent (smaller than the smallest)
                int randomValue = array[rand.nextInt(n)]; // randomly chosen value from array

                // Output the size of the array and the values to search for
                System.out.println(n);
                System.out.println(valueAtStart);
                System.out.println(valueAtMiddle);
                System.out.println(valueAtEnd);
                System.out.println(nonExistentValue);
                System.out.println(randomValue);
                outputArray(array);
            }
        }
    }

    public static int[] generateRandomArray(int n) {
        Random rand = new Random();
        return rand.ints(n, 0, 2 * n).toArray();
    }

    private static int[] generateAscendingArray(int n) {
        int[] arr = generateRandomArray(n);
        Arrays.sort(arr);
        return arr;
    }

    private static void outputArray(int[] array) {
        for (int value : array) {
            System.out.println(value);
        }
    }
}
































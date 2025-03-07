import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class BinarySearch1 {

    private static int comparisons = 0;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                int n = Integer.parseInt(line); // Read n
                // Read values to search for
                int valueAtStart = Integer.parseInt(reader.readLine());
                int valueAtMiddle = Integer.parseInt(reader.readLine());
                int valueAtEnd = Integer.parseInt(reader.readLine());
                int nonExistentValue = Integer.parseInt(reader.readLine());
                int randomValue = Integer.parseInt(reader.readLine());

                int[] array = new int[n];
                for (int i = 0; i < n; i++) {
                    array[i] = Integer.parseInt(reader.readLine()); // Read each array element
                }

                // Perform search and record data for each value
                performSearchAndRecordData(array, n, valueAtStart, "BINARYSEARCH_STARTVALUE.txt");
                performSearchAndRecordData(array, n, valueAtMiddle, "BINARYSEARCH_MIDDLEVALUE.txt");
                performSearchAndRecordData(array, n, valueAtEnd, "BINARYSEARCH_ENDVALUE.txt");
                performSearchAndRecordData(array, n, nonExistentValue, "BINARYSEARCH_NONEXISTVALUE.txt");
                performSearchAndRecordData(array, n, randomValue, "BINARYSEARCH_RANDOMVALUE.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void performSearchAndRecordData(int[] array, int n, int v, String fileName) throws IOException {
        resetCounters();
        long startTime = System.nanoTime();
        int result = binarySearch(array, 0, n - 1, v);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        recordData(fileName, n, v, comparisons, duration, result);
        System.out.println("Value: " + v + " Found: " + result + " Comparisons: " + comparisons + " Time: " + duration + "ms");
        resetCounters();
    }

    public static int binarySearch(int[] array, int left, int right, int value) {
        if (right >= left) {
            int mid = left + (right - left) / 2;
            int comp = compare(array[mid], value);

            if (comp == 0) {
                return 1; // Value found
            } else if (comp > 0) {
                return binarySearch(array, left, mid - 1, value);
            } else {
                return binarySearch(array, mid + 1, right, value);
            }
        }
        return 0; // Value not found
    }

    private static int compare(int a, int b) {
        comparisons++;
        return Integer.compare(a, b);
    }

    private static void resetCounters() {
        comparisons = 0;
    }

    private static void recordData(String fileName, int n, int v, int comparisons, long time, int found) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(String.format("Array Size: %d, Search Value: %d, Comparisons: %d, Time: %dms, Found: %d\n", n, v, comparisons, time, found));
        }
    }
}






























import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class DataGenerator {
    public static void main(String[] args) {
        String mode = args[0];
        List<Integer> nparam = new ArrayList<>();
        for (int i = 10; i <= 50; i = i + 10) {
            nparam.add(i);
        }
        for (int i = 1000; i <= 50000; i = i + 1000) {
            nparam.add(i);
        }
        int[] kparam = {1,10,100};  // Number of repetitions
        for (int n : nparam) {
            for (int k : kparam) {
                for (int i = 0; i < k; i++) {
                    int[] array = switch (mode) {
                        case "random" -> generateRandomArray(n);
                        case "ascending" -> generateAscendingArray(n);
                        case "descending" -> generateDescendingArray(n);
                        default -> throw new IllegalArgumentException("Unknown mode: " + mode);
                    };

                    // Output the generated array
                    for (int j : array) {
                        System.out.println(j);
                    }
                }
            }
        }
    }

    private static int[] generateRandomArray(int n) {
        Random rand = new Random();
        return rand.ints(n, 0, 2 * n).toArray();
    }

    private static int[] generateAscendingArray(int n) {
        int[] arr = generateRandomArray(n);
        Arrays.sort(arr);
        return arr;
    }

    private static int[] generateDescendingArray(int n) {
        int[] arr = generateRandomArray(n);
        Arrays.sort(arr);
        for (int i = 0; i < n/ 2; i++) {
            int temp = arr[i];
            arr[i] = arr[n - i - 1];
            arr[n - i - 1] = temp;
        }
        return arr;
    }
}

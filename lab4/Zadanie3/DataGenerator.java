import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class DataGenerator {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[1]);
        String mode = args[0];

        int[] array = switch (mode) {
            case "random" -> generateRandomArray(n);
            case "ascending" -> generateAscendingArray(n);
            default -> throw new IllegalArgumentException("Unknown mode: " + mode);
        };

        // Output the generated array
        System.out.println(n);
        for (int j : array) {
            System.out.println(j);
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

}
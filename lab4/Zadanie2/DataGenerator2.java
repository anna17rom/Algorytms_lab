import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.io.*;

public class DataGenerator2 {
    private static final int MAX_N = 100000;
    private static final int STEP = 10000;
    private static final int M = 20;

    public static void main(String[] args) throws IOException {
        for (int n = 10000; n <= MAX_N; n += STEP) {
            for (int i = 0; i < M; i++) {
                int[] array = generateAscendingArray(n);
                System.out.println(n);
                for (int j : array) {
                    System.out.println(j);
                }
            }
        }

    }

    public static int[] generateRandomArray ( int n){
        Random rand = new Random();
        return rand.ints(n, 0, 2 * n).toArray();
    }
    private static int[] generateAscendingArray(int n) {
        int[] arr = generateRandomArray(n);
        Arrays.sort(arr);
        return arr;
    }

}




































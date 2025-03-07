import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.io.*;

public class DataGenerator2 {
    private static final int MAX_N = 50000;
    private static final int STEP = 100;
    private static final int M = 50;

    public static void main(String[] args) throws IOException {
            for (int n = 100; n <= MAX_N; n += STEP) {
                    for (int i = 0; i < M; i++) {
                        int percentK = new Random().nextInt(101);
                        int k = Math.max(1, (percentK * n) / 100);
                        int[] array = generateRandomArray(n);
                        // Output the generated array
                        System.out.println(n);
                        System.out.println(k);
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

    }



























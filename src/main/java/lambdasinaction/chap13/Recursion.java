package lambdasinaction.chap13;

import java.util.stream.LongStream;


public class Recursion {

    public static void main(String[] args) {
        long startTime1 = System.nanoTime();

        System.out.println(factorialIterative(9));
        long startTime2 = System.nanoTime();
        System.out.println("消耗时间：" + (startTime2 - startTime1));

        System.out.println(factorialRecursive(9));
        long startTime3 = System.nanoTime();
        System.out.println("消耗时间：" + (startTime3 - startTime2));

        System.out.println(factorialStreams(9));
        long startTime4 = System.nanoTime();
        System.out.println("消耗时间：" + (startTime4 - startTime3));

        System.out.println(factorialTailRecursive(9));
        long startTime5 = System.nanoTime();
        System.out.println("消耗时间：" + (startTime5 - startTime4));
    }

    public static int factorialIterative(int n) {
        int r = 1;
        for (int i = 1; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    public static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }

    public static long factorialStreams(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(1, (long a, long b) -> a * b);
    }

    public static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

    public static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }
}

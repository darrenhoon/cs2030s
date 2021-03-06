import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.stream.Stream;

public class Main {

    static boolean isPerfect(int n) {
        IntStream intStream = IntStream.range(1,n / 2 + 1);
        intStream = intStream.filter(x -> n % x == 0);
        return intStream.sum() == n;
    }

    static boolean isSquare(int n) {
        IntStream intStream = IntStream.range(1, n);
        intStream = intStream.filter(x -> n - (x * x) == 0);
        return intStream.count() > 0;
    }

    static long countRepeats(int[] array) {
        IntStream intStream = IntStream.range(0, array.length - 1);
        intStream = intStream.filter(x -> 
                array[x] != array[x + 1] ?
                false :
                x <= 1 ?
                false :
                array[x - 1] == array[x] ?
                false :
                true
                );
        return intStream.count(); 

    }

    static boolean isPrime(int n) {
        return IntStream.range(2, n).noneMatch(x -> n%x == 0);    
    }

    static int[] twinPrimes(int n) {
        return IntStream
            .rangeClosed(2, n)
            .filter(x -> Main.isPrime(x))
            .filter(x -> (Main.isPrime(x - 2) || Main.isPrime(x + 2)) && (x != 2))
            .toArray();
    }

    static int gcd(int m, int n) {
        return Stream.iterate(new Pair(m, n), pair -> pair.b != 0, pair -> new Pair(pair.b, pair.a % pair.b)).sorted()
            .findFirst().get().b;
    }

    static OptionalDouble variance(int[] original) {
        double total = original.length;
        if (total < 2) {
            return OptionalDouble.empty();
        }
        final double mean = (Arrays.stream(original).sum()) / total;

        double temp = Arrays.stream(original).mapToDouble(
                x -> (x - mean) * (x - mean)).sum();
        temp = temp / (total - 1);
        return OptionalDouble.of(temp);

    }

    public static void main(String[] args) {

        //Initialize Input/Output
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);

        int n;

        //Level 4: Read in multiple integers and form an array
        //         Output the variance
        ArrayList<Integer> test = new ArrayList<Integer>();
        while (sc.hasNext()) {
            test.add(sc.nextInt());
        }

        int[] array = new int[test.size()];
        for (int z = 0; z < test.size(); z++) {
            array[z] = test.get(z);
        }

        pw.println(variance(array));

        pw.flush();

    }

}

import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.function.IntFunction;

public class Main {
    
    public static void main(String[] args) {
    }

    static boolean isPrime(int n) {
        return IntStream
            .rangeClosed(2,n)
            .noneMatch(x -> x*n == 0);
    }
    static int[] twinPrimes(int n) {
        int[] a;
        return Stream
            .iterate(2, x -> Main.isPrime(x), x -> x + 1)
            .collect(Collectors.toList()).toArray(a);
    }
}
 

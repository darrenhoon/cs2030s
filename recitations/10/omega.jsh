import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.function.LongToIntFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

boolean isPrime(int n) {
    return IntStream
        .rangeClosed(2, n)
        .noneMatch(x -> n%x == 0);
}

LongStream omega(int n) {
    return IntStream.iterate(n, x -> x + 1)
    .filter(x -> isPrime(x))
    .mapToLong(x -> x);
}


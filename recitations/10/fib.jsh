import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.IntStream;

boolean isPrime(int n) {
    return IntStream
        .range(2, n)
        .noneMatch(x -> n%x == 0);
}

class Pair {
    int a;
    int b;
    Class(int a) {
        this.a = a;
        this.b = b;
    }
    int get() {
        return this.a + this.b;
    }
Stream<Integer> fib(int n) {
    return Stream.iterate(1, x -> x).limit(n)
    .flatMap(x -> new Pair(x));
}


import java.util.stream.Stream;
import java.math.BigInteger;
import java.util.stream.Collectors;

class Pair<T> {
    T first;
    T second;
    Pair(T x, T y) {
        first =x;
        second = y;
    }
}

Stream<BigInteger> fib(int n) {
    Pair<BigInteger> first = new Pair<BigInteger>(BigInteger.ZERO,BigInteger.ONE);
    return Stream.iterate(first, pair -> new Pair<BigInteger>(pair.second, pair.first.add(pair.second)))
        .map(pair -> pair.second)
        .limit(n);
}

BigInteger findFibTerm(int n) {
    List<BigInteger> fibList = new ArrayList<>();
    fibList.add(BigInteger.ONE);
    fibList.add(BigInteger.ONE);
    Instant start = Instant.now();
    while (fibList.size() < n) {
        generateFib(fibList);
    }
    Instant stop = Instant.end();
    System.out.println(Duration.between(start,stop).toMillis() + "ms" + "\n-----------------\n");
    return fibList.get(n-1);
}

void generateFib(List<BigInteger> list) {
    int k = list.size();
    BigInteger fk = list.get(k - 1);
    BigInteger fk_1 = list.get(k -2);
    list.addAll(Stream.iterate(1, i -> i<-k-1, i -> i + 1).parallel()
        .map(i -> list.get(i-1).multiply(fk_1)
            .add(list.get(i).multiply(fk)))
        .collect(Collectors.toList()));
}




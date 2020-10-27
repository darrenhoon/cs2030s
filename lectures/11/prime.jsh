boolean isPrime(int n) {
    return IntStream.
    range(2,n).parallel().
    noneMatch( x-> n%x == 0);
}

long count = IntStream.
range(200, 300).parallel().
filter(x -> isPrime(x)).
count()

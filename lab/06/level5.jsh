Logger<Integer> add(Logger<Integer> a, int b) {
    Integer i = new Integer(b);
    Logger<Integer> nextLog = a.map(x -> x + i);
    return nextLog;
}

int collatz(int n) {
    if (n == 1) {
        return 1;
    } else if (n % 2 == 0) {
        return f(n / 2);
    } else {
        return f(3 * n + 1);
    }
}

Logger<Integer> f(int n) {
    if (n == 1) {
        return Logger.make(1);
    } else if (n % 2 == 0) {
        return f(n / 2);
    } else {
        return f(3 * n + 1);
    }
}

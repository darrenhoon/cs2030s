
Logger<Integer> add(Logger<Integer> a, int b) {
    Integer i = new Integer(b);
    Logger<Integer> nextLog = a.map(x -> x + i);
    return nextLog;
}

Logger<Integer> sum(int n) {
    if (n == 0) {
        return Logger.make(0);
    } else {
        return add(sum(n-1), n);
    }
}

Logger<Integer> f(int n) {

    Logger<Integer> log = Logger.make(n);

    while(n !=1) {
        if (n % 2 == 0) {
            log = log.map(x -> x/2);
            n = n / 2;
        } else {
            log = log.map(x -> x * 3).map(x -> x + 1);
            n = 3 * n + 1;
        }
    }
    return log;
}

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.List;
public class Primes {
    static void findKthPrimeLL(int a, int b, int k) {
        int prime = LazyList.intRange(a, b)
            .filter(predInstrument(Primes::isPrime))
            .get(k-1);
        printPrime(k, prime);
        printCounter();
    }
    static void findKthPrimeFromBackLL(int a, int b, int k){
        int prime = LazyList.intRange(a, b)
            .filter(predInstrument(Primes::isPrime))
            .reverse()
            .get(k-1);
        printPrime(k, prime);
        printCounter();
    }
    static void findKthPrimeArr(int a, int b, int k) {
        var primesArrayList = listFilter(predInstrument(Primes::isPrime),
                                         intRangeArr(a, b));
        int prime = primesArrayList.get(k - 1);
        printPrime(k, prime);
        printCounter();
    }
    static void findKthPrimeFromBackArr(int a, int b, int k) {
        var primesArrayList = listFilter(predInstrument(Primes::isPrime),
                                         intRangeArr(a, b));
        int prime = primesArrayList.get(primesArrayList.size()-k);
        printPrime(k, prime);
        printCounter();
    }
    static int counter = 0;
    static void incrCounter() {
        counter++;
    }
    static void printCounter() {
        System.out.println(String.format("isPrime was called %d times", counter));
    }
    static void printUsage() {
        System.out.println("Usage: java Primes <a> <b> <k> <w>");
        System.out.println("where:  <a> is the lower limit of the range,");
        System.out.println("\t<b> is the upper limit of the range,") ;
        System.out.println("\t<k> is the k-th prime to find,");
        System.out.println("\t<w> is 0 (run the ArrayList version) or 1 (the LazyList version)");
        System.out.println("\n*** Note that <a> must be larger than 1.\n");
        System.out.println("To time how fast it runs, use the time bash command");
        System.out.println("\t\t eg: time java Primes 2 1000 3 1");
        System.out.println("\twill find the 3rd prime between 2 and 1000 using");
        System.out.println("\tthe LazyList version, and report the time taken to run.\n");
    }
    static int[] parse(String[] args) {
        if (args.length != 4) {
            printUsage();
            throw new RuntimeException();
        }
        int[] result = new int[4];
        for (int i = 0; i < 4; i++)
            result[i] = Integer.parseInt(args[i]);
        return result;
    }
    static void printPrime(int k, int p) {
        System.out.println(String.format("The %d-th prime is %d", k, p));
    }
    static <T> Predicate<T> predInstrument(Predicate<T> p) {
        return x-> { incrCounter();
                     return p.test(x);};
    }
    static List<Integer> intRangeArr(int a, int b) {
        List<Integer> result = new ArrayList<>();
        for (int x = a; x < b; x++)
            result.add(x);
        return result;
    }
    static <T> List<T> listFilter(Predicate<T> p, List<T> list) {
        List<T> newList = new ArrayList<>();
        for (T item : list)
            if (p.test(item))
                newList.add(item);
        return newList;
    }
    static boolean isPrime(int n) {
        for (int i = 2; i*i <= n; i++)
            if (n%i == 0)
                return false;
        return true;
    }
    public static void main(String[] args) {
        var inputs = parse(args);
        if (inputs[3] == 0)
            findKthPrimeArr(inputs[0], inputs[1], inputs[2]);
        else if (inputs[3] == 1)
            findKthPrimeLL(inputs[0], inputs[1], inputs[2]);
        else if (inputs[3] == 2)
            findKthPrimeFromBackArr(inputs[0], inputs[1], inputs[2]);
        else if (inputs[3] == 3)
            findKthPrimeFromBackLL(inputs[0], inputs[1], inputs[2]);
        else
            System.out.println("Wrong value for <w>!");
    }
}

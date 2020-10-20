public class Puzzle {
    static boolean pzczSatisfies(LazyList<Integer> term) {
        int c = term.get(0);
        int h = term.get(1);
        int m = term.get(2);
        int p = term.get(3);
        int u = term.get(4);
        int z = term.get(5);
    }
    public static LazyList<LazyList<Integer>> permute(LazyList<Integer> LL, int r) {
        if (r == 1)
            return LL.map(x-> new LazyList<>((x), ( ()->(LazyList.makeEmpty()) )));
        else
            return LL.map(x -> new LazyList<>((x), ( ()->(permute(remove(LL, x), x - 1)) )));
    }
    public static LazyList<Integer> remove(LazyList<Integer> LL, int n) {
        return LL.filter(x-> x != n);
    }
    public static void main(String[] args) {
        permute(LazyList.intRange(1,10), 6)
            .filter(Puzzle::pzczSatisfies)
            .forEach(LazyList::print);
    }
}

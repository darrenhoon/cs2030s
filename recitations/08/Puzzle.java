
public class Puzzle {

    static boolean pzczSatisfies(LazyList<Integer> term) {
        int c = term.get(0);
        int h = term.get(1);
        int m = term.get(2);
        int p = term.get(3);
        int u = term.get(4);
        int z = term.get(5);

        //Insert your code here.
        //Return true only when both m,p are not 0,
        //and the given equation is satisfied.
        //only possible because all the alphabets are permutations ie neither will have the same of value
        int pzcz = p*1000 + z*100 + c*100 + z;
        int muchz = m*10000 + u*1000 + c*100 + h*10 + z;
        return m!=0 && p!= 0 && (pzcz*15 == muchz);
    }

    public static LazyList<LazyList<Integer>> permute(LazyList<Integer> LL, int r) {
        if (r == 1)
            return LL.map(x-> LLmake(x, LazyList.makeEmpty()));
        else
            // Insert code here
    }

    public static LazyList<Integer> remove(LazyList<Integer> LL, int n) {
        return LL.filter(x-> x != n);
    }

    //permuate will return a list of lists of size 10P6
    public static void main(String[] args) {
        permute(LazyList.intRange(0,10), 6)
            .filter(Puzzle::pzczSatisfies)
            .forEach(LazyList::print);
    }
}

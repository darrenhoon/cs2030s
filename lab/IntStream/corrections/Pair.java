public class Pair implements Comparable<Pair> {

    final int a;
    final int b;

    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int compareTo(Pair other) {
        return this.a - other.a;
    }

}

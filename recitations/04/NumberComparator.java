import java.lang.Comparable;
import java.util.Comparator;

class NumberComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer s1, Integer s2) {
        return s1 - s2;
    }
}

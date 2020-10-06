import java.util.Comparator;
class ClassComparator implements Comparator<Lecture> { 
    public int compare(Lecture a, Lecture b) {
        if (Double.compare(a.getStartTime(), b.getStartTime()) !=0) {
            return Double.compare(a.getStartTime(), b.getStartTime());
        }
        if (Double.compare(a.getEndTime(), b.getEndTime()) !=0) {
            return Double.compare(a.getEndTime(), b.getEndTime());
        }
        return Integer.compare(a.getClassId(), b.getClassId());
    }
}

//lines added: 6 to 12

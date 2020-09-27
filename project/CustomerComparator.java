import java.util.Comparator;

class CustomerComparator implements Comparator<Customer> {
    
    @Override
    public int compare(Customer a, Customer b) {
        double atime = a.getArrivalTime();
        double btime = b.getArrivalTime();
        if (atime <= btime) {
            return -1;
        }
        if (atime > btime) {
            return 1;
        }
        return 0;
    }
}

import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    
    @Override
    public int compare(Event a, Event b) {
        Event prev = a;
        Event current = b;
        while !(prev instanceof DoneEvent) {
            prev = prev.execute();
        }
        double atime = a.getCurrentTime();
        double btime = b.getCurrentTime();

        if (atime <= btime) {
            return -1;
        }
        if (atime > btime) {
            return 1;
        }
        return 0;
    }
}

import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    
    @Override
    public int compare(Event a, Event b) {
        double timeBefore = a.getCurrentTime();
        double timeCurrent = b.getCurrentTime();
        
        if (timeBefore < timeCurrent) {
            return -1;
        }
        if (timeBefore > timeCurrent) {
            return 1;
        }
        if (timeBefore == timeCurrent) {
            if (a instanceof DoneEvent && !(b instanceof DoneEvent)) {
                return -1;
            }
            if (!(a instanceof DoneEvent) && b instanceof DoneEvent) {
                return 1;
            }
        }
        return 0;
    }
}

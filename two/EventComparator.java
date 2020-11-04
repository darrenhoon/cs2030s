package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event a, Event b) {
        double timeBefore = a.getCurrentTime();
        double timeCurrent = b.getCurrentTime();

        if (Double.compare(timeBefore, timeCurrent) != 0) {
            return Double.compare(timeBefore, timeCurrent);
        }

        if (a instanceof DoneEvent) {
            return -1;
        }
        if (b instanceof DoneEvent) {
            return 1;
        }

        if (a instanceof ArriveEvent) {
            return 1;
        }
        if (a instanceof ServeEvent) {
            return -1;
        }
        return 0;
    }
}

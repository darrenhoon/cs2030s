//package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    private static final int SMALLER = -1;
    private static final int EQUAL = 0;
    private static final int LARGER = 1;

    /**
     * Compares the events based on time and 
     * type of event to establish priority.
     * @param event1 one type of event
     * @param event2 one type of event
     * @return a value to show which event has a higher priority
     */

    @Override
    public int compare(Event event1, Event event2) {
        double time1;
        double time2;

        if (event1 instanceof ServeEvent) {
            double arrivalTime = event1.customer().arrivalTime();
            double availableTime = event1.server().nextAvailableTime();
            if (availableTime > arrivalTime) {
                time1 = availableTime;
            } else {
                time1 = arrivalTime;
            }
        } else {
            time1 = event1.customer().arrivalTime();
        }
        
        if (event2 instanceof ServeEvent) {
            double arrivalTime = event2.customer().arrivalTime();
            double availableTime = event2.server().nextAvailableTime();
            if (availableTime > arrivalTime) {
                time2 = availableTime;
            } else {
                time2 = arrivalTime;
            }
        } else {
            time2 = event2.customer().arrivalTime();
        }

        if (time1 < time2) {
            return SMALLER;
        } else if (time1 > time2) {
            return LARGER;
        } else {
            if (event1 instanceof DoneEvent) {
                return SMALLER;
            } else if (event2 instanceof DoneEvent) {
                return LARGER;
            } else if (event1 instanceof ArriveEvent) {
                return LARGER;
            } else if (event1.customer().identifier() > event2.customer().identifier()) {
                return LARGER;
            } else if (event1.customer().identifier() < event2.customer().identifier()) {
                return SMALLER;
            }
            return EQUAL;
        }
    }
    /*
       @Override
       public int compare(Event a, Event b) {
       double timeBefore = a.customer().arrivalTime();
       double timeCurrent = b.customer().arrivalTime();

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
       */
}

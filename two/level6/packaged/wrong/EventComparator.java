package cs2030.simulator;

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
        double time1; //= event1.server().nextAvailableTime();
        double time2; //= event2.server().nextAvailableTime();

        if (event1 instanceof ArriveEvent) {
            time1 = event1.customer().arrivalTime();
        } else if (event1 instanceof SERVER_REST || event1 instanceof SERVER_BACK) {
            time1 = event1.server().nextAvailableTime();
        } else if (event1 instanceof ServeEvent) {
            time1 = event1.server().nextAvailableTime();
        } else if (event1 instanceof WaitEvent) {
            time1 = event1.customer().arrivalTime();
        } else if (event1 instanceof DoneEvent) {
            time1 = event1.server().nextAvailableTime();
        } else {
            time1 = event1.customer().arrivalTime(); // LeaveEvent
        }

        if (event2 instanceof ArriveEvent) {
            time2 = event2.customer().arrivalTime();
        } else if (event2 instanceof SERVER_REST || event2 instanceof SERVER_BACK) {
            time2 = event2.server().nextAvailableTime();
        } else if (event2 instanceof ServeEvent) {
            time2 = event2.server().nextAvailableTime();
        } else if (event2 instanceof WaitEvent) {
            time2 = event2.customer().arrivalTime();
        } else if (event2 instanceof DoneEvent) {
            time2 = event2.server().nextAvailableTime();
        } else {
            time2 = event2.customer().arrivalTime(); // LeaveEvent
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
            } else if (event1.customer().identifier() < event2.customer().identifier()) {
                return SMALLER;
            }  else if (event1.customer().identifier() > event2.customer().identifier()) {
                return LARGER;
            } 
            return EQUAL;
        }
    }
}

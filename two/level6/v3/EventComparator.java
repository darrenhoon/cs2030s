//package cs2030.simulator;

import java.util.Comparator;

/**
 * Compares which events should have a higher priority.
 * */
class EventComparator implements Comparator<Event> {
    private static final int SMALLER = -1;
    private static final int EQUAL = 0;
    private static final int LARGER = 1;

    /**
     * Compares the events based on time and 
     * type of event to establish priority.
     * @param event1 one type of event
     * @param event2 one type of event
     * @return       a value to show which event has a higher priority
     */
    @Override
    public int compare(Event event1, Event event2) {
        double time1;
        double time2;
        int priority1 = event1.getPriority();
        int priority2 = event2.getPriority();
        int id1 = event1.getCustomer().getId();
        int id2 = event2.getCustomer().getId();

        // if (event1 instanceof ArriveEvent || event1 instanceof WaitEvent) {
        //     time1 = event1.getCustomer().getArrivalTime();
        // } else {
        //     double arrivalTime = event1.getCustomer().getArrivalTime();
        //     double availableTime = event1.getServer().getNextAvailableTime();
        //     if (availableTime > arrivalTime) {
        //         time1 = availableTime;
        //     } else {
        //         time1 = arrivalTime;
        //     }
        // }

        if (event1 instanceof ServeEvent|| event1 instanceof DoneEvent) {
            double arrivalTime = event1.getCustomer().getArrivalTime();
            double availableTime = event1.getServer().getNextAvailableTime();
            if (availableTime > arrivalTime) {
                time1 = availableTime;
            } else {
                time1 = arrivalTime;
            }
        } else if (event1 instanceof ContinueWaitEvent|| event1 instanceof ServerRest || event1 instanceof ServerBack) {
            time1 = event1.getServer().getNextAvailableTime();
        } else {
            time1 = event1.getCustomer().getArrivalTime();
        }

        // if (event2 instanceof ArriveEvent || event2 instanceof WaitEvent) {
        //     time2 = event2.getCustomer().getArrivalTime();
        // } else {
        //     double arrivalTime = event2.getCustomer().getArrivalTime();
        //     double availableTime = event2.getServer().getNextAvailableTime();
        //     if (availableTime > arrivalTime) {
        //         time2 = availableTime;
        //     } else {
        //         time2 = arrivalTime;
        //     }
        // }

        if (event2 instanceof ServeEvent|| event2 instanceof DoneEvent) {
            double arrivalTime = event2.getCustomer().getArrivalTime();
            double availableTime = event2.getServer().getNextAvailableTime();
            if (availableTime > arrivalTime) {
                time2 = availableTime;
            } else {
                time2 = arrivalTime;
            }
        } else if (event2 instanceof ContinueWaitEvent|| event2 instanceof ServerRest || event2 instanceof ServerBack) {
            time2 = event2.getServer().getNextAvailableTime();
        } else {
            time2 = event2.getCustomer().getArrivalTime();
        }

        if (time1 < time2) {
            return SMALLER;
        } else if (time1 > time2) {
            return LARGER;
        } else {
            if (priority1 < priority2) {
                return SMALLER;
            } else if (priority1 > priority2) {
                return LARGER;
            } else {
                if (id1 < id2) {
                    return SMALLER;
                } else if (id1 > id2) {
                    return LARGER;
                }
            }
            return EQUAL;
        }
    }
}

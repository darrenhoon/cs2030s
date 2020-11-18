//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Creates the flow of events from start to end.
 */
public class ArriveEvent extends Event {
    public static final int RESTING = 1;
    public static final int BACK = 1;
    public static final int ARRIVES = 2;
    public static final int DONE = 3;
    public static final int LEAVES = 4;
    public static final int SERVED = 5;
    public static final int WAITS = 6;
    public static final int WAITING = 7;

    /**
     * Creates an arrive event.
     * @param customer an instance of customer
     */
    public ArriveEvent(Customer customer) {
        super(customer,  shop -> {
            //return ServeEvent
            if (shop.find(server -> server.isAvailable()).isPresent()) {
                Server s1 = shop.find(x -> x.isAvailable()).get();
                return Pair.of(shop, new ServeEvent(customer, s1));
            } 
            //return WaitEvent
            if (shop.find(server -> server.canQueue()).isPresent()) {
                Server s1 = shop.find(x -> x.canQueue()).get();
                return Pair.of(shop, new WaitEvent(customer, s1));
            }
            return Pair.of(shop, new LeaveEvent(customer));
        }, ARRIVES);
    }

    /**
     * Returns the string representation.
     */
    public String toString() {
        double arrivalTime = this.getCustomer().getArrivalTime();
        int id = this.getCustomer().getId();
        return String.format("%.3f %s arrives", arrivalTime, id);
    }
}
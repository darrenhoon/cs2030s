//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Represents the done event which completes the flow of events.
 */
public class DoneEvent extends Event {
    public static final int RESTING = 1;
    public static final int BACK = 1;
    public static final int ARRIVES = 2;
    public static final int DONE = 3;
    public static final int LEAVES = 4;
    public static final int SERVED = 5;
    public static final int WAITS = 6;
    public static final int WAITING = 7;

    /**
     * Creates a done event.
     * @param customer   an instance of a customer
     * @param servers    a list of servers
     * @param server     an instance of a server
     * @param timeServed value representing the time the server served a customer
     */
    public DoneEvent(Customer customer, Server server) {
        super(customer, shop -> {

            int currentId = server.getId();
            Server currentServer = shop.find(s -> s.getId() == currentId).get();

            //information needed from server to be passed on
            double nextTiming = currentServer.getNextAvailableTime();
            int maxQLength = currentServer.maxQ();
            int numOfWaitingCustomer = currentServer.numOfWaitingCustomer();

            Server nextServer;
            Shop nextShop;
            Event nextEvent;

            //For normal server not self-checkout counters
            if (currentServer instanceof SelfCheckout) {
                nextServer = new SelfCheckout(currentId, true, numOfWaitingCustomer, nextTiming, maxQLength);
                nextShop = shop.replace(nextServer);
                nextEvent = null;
            } else {
                double randomRest = Event.getRandomRest();
                if (randomRest < Server.getRestingProb()) {
                    nextServer = new Server(currentId, true, numOfWaitingCustomer, nextTiming, maxQLength, true);
                    nextShop = shop.replace(nextServer);
                    nextEvent = new ServerRest(customer, nextServer);
                } else { //change server to available and keep numOfWaitingCustomers
                    nextServer = new Server(currentId, true, numOfWaitingCustomer, nextTiming, maxQLength);
                    nextShop = shop.replace(nextServer);
                    nextEvent = null;
                }
            }
            return Pair.of(nextShop, nextEvent);
        }, server, DONE);
    }

    /**
     * Returns the string representation.
     */
    public String toString() {
        double doneTime = this.getServer().getNextAvailableTime();
        return String.format("%.3f %s done serving by %s", 
                doneTime, this.getCustomer().getId(), this.getServer().getName());
    }
}

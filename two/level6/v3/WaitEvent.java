//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a wait event which comes after an arrive event.
 */
public class WaitEvent extends Event {
    public static final int ARRIVES = 1;
    public static final int RESTING = 2;
    public static final int BACK = 2;
    public static final int DONE = 3;
    public static final int LEAVES = 4;
    public static final int SERVED = 5;
    public static final int WAITS = 6;
    public static final int WAITING = 7;

    /**
     * Creates a wait event.
     * @param customer an instance of a customer
     * @param servers  a list of servers
     * @param server   an instance of a server
     * @param nextTime value representing the next time the server is available
     */    
    public WaitEvent(Customer customer, Server server) {
        super(customer, shop -> {

            int currentId = server.getId();

            Server currentServer = shop.find(s -> s.getId() == currentId).get();

            //information needed from server to be passed on
            double nextTiming = currentServer.getNextAvailableTime();
            int totalWaitingCustomers = currentServer.numOfWaitingCustomer() + 1;
            int maxQLength = currentServer.maxQ();
            boolean isResting = currentServer.isResting();

            Server nextServer;

            if (currentServer instanceof SelfCheckout) {
                nextServer = new SelfCheckout(currentId, false, totalWaitingCustomers, nextTiming, maxQLength);
            } else {
                nextServer = new Server(currentId, false, totalWaitingCustomers, nextTiming, maxQLength, isResting);
            }
            Shop nextShop = shop.replace(nextServer);
            Event nextEvent;

            if (currentServer instanceof SelfCheckout) {
                while (shop.find(s -> s.getId() != currentId && s.numOfWaitingCustomer() != totalWaitingCustomers).isPresent()) {
                    Server selfcheck = shop.find(s -> s.getId() != currentId && s.numOfWaitingCustomer() != totalWaitingCustomers - 1).get();
                    int id = selfcheck.getId();
                    boolean available = selfcheck.isAvailable();
                    int waitingCustomer = totalWaitingCustomers;
                    double nextTime = selfcheck.getNextAvailableTime();
                    int maxQ = selfcheck.maxQ();
                    Server newServer = new SelfCheckout(id, available, waitingCustomer, nextTime, maxQ);
                    nextShop = nextShop.replace(newServer);
                }
            }

            if ((currentServer.isAvailable() && currentServer.numOfWaitingCustomer() == 0) && !currentServer.isResting()) {
                nextEvent = new ServeEvent(customer, nextServer);
            } else {
                nextEvent = new ContinueWaitEvent(customer, nextServer);
            }

            return Pair.of(nextShop, nextEvent);
        }, server, WAITS);
    }

    /**
     * Returns the string representation.
     */
    @Override
    public String toString() {
        double waitTime = this.getCustomer().getArrivalTime();
        return String.format("%.3f %s waits to be served by %s", 
                waitTime, this.getCustomer().getId(), this.getServer().getName());
    }
}

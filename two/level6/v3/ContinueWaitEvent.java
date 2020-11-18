//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a wait event which comes after an arrive event.
 */
public class ContinueWaitEvent extends Event {
    public static final int RESTING = 1;
    public static final int BACK = 1;
    public static final int ARRIVES = 2;
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
    public ContinueWaitEvent(Customer customer, Server server) {
        super(customer, shop -> {

            int currentId = server.getId();
            Server currentServer = shop.find(s -> s.getId() == currentId).get();

            //information needed from server to be passed on
            double nextTiming = currentServer.getNextAvailableTime();
            int totalWaitingCustomers = currentServer.numOfWaitingCustomer();
            int maxQLength = currentServer.maxQ();
            boolean isResting = currentServer.isResting();

            Server nextServer;
            Shop nextShop;
            Event nextEvent;

            if (currentServer.isAvailable()) {
                //return new ServeEvent if Server available
                nextServer = new Server(currentId, false, totalWaitingCustomers, nextTiming, maxQLength, isResting);
                nextEvent = new ServeEvent(customer, nextServer);
            } else {
                //return new ContinueWaitEvent if Server unavailable
                nextServer = new Server(currentId, false, totalWaitingCustomers, nextTiming, maxQLength, isResting);
                nextEvent = new ContinueWaitEvent(customer, nextServer);
            }
            nextShop = shop.replace(nextServer);
            return Pair.of(nextShop, nextEvent);
        }, server, WAITING);
    }

    @Override
    public String toString() {
        double waitTime = this.getServer().getNextAvailableTime();
        return String.format("%.3f %s continues to wait to be served by %s", 
                waitTime, this.getCustomer().getId(), this.getServer().getName());
    }
}
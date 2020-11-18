//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a serve event which comes after
 * an arrive event or a wait event.
 */
public class ServeEvent extends Event {
    public static final int RESTING = 1;
    public static final int BACK = 1;
    public static final int ARRIVES = 2;
    public static final int DONE = 3;
    public static final int LEAVES = 4;
    public static final int SERVED = 5;
    public static final int WAITS = 6;
    public static final int WAITING = 7;

    /**
     * Creates a serve event.
     * @param customer          an instance of a customer
     * @param shop              a shop of servers
     * @param server            an instance of a server
     * @param nextAvailableTime value representing the next time the server is available
     */
    public ServeEvent(Customer customer, Server server) {
        super(customer, shop -> {
            int currentId = server.getId();
            Server currentServer = shop.find(s -> s.getId() == currentId).get();

            //information needed from server to be passed on
            double availableTime = currentServer.getNextAvailableTime();
            int maxQLength = currentServer.maxQ();

            //information needed from customer to be passed on
            double arrivalTime = customer.getArrivalTime();
            double currentTime;
            if (availableTime > arrivalTime) {
                currentTime = availableTime;
            } else {
                currentTime = arrivalTime;
            }

            //Random Service time
            double SERVICE_TIME = Event.getServiceTime();

            Server nextServer;
            double nextTiming = currentTime + SERVICE_TIME;
            int numOfWaitingCustomer = currentServer.numOfWaitingCustomer();

            //Replace one server's status only for server but checks if server is a self-checkout counter
            if (currentServer instanceof SelfCheckout) {
                if (numOfWaitingCustomer == 0) {
                    nextServer = new SelfCheckout(currentId, false, 0, nextTiming, maxQLength);
                } else {
                    nextServer = new SelfCheckout(currentId, false, numOfWaitingCustomer - 1, nextTiming, maxQLength);
                }
            } else {
                if (numOfWaitingCustomer == 0) {
                    nextServer = new Server(currentId, false, 0, nextTiming, maxQLength);
                } else {
                    nextServer = new Server(currentId, false, numOfWaitingCustomer - 1, nextTiming, maxQLength);
                }
            }

            //Creation nextShop variable
            Shop nextShop = shop.replace(nextServer);

            //Replace other self-checkout counters numOfWaitingCustomers if waiting customers not equals 0
            if (currentServer instanceof SelfCheckout) {
                if (numOfWaitingCustomer != 0) {
                    while (shop.find(s -> s.getId() != currentId && s.numOfWaitingCustomer() != numOfWaitingCustomer - 1).isPresent()) {
                        Server selfcheck = shop.find(s -> s.getId() != currentId && s.numOfWaitingCustomer() != numOfWaitingCustomer - 1).get();
                        int id = selfcheck.getId();
                        boolean available = selfcheck.isAvailable();
                        int waitingCustomer = numOfWaitingCustomer - 1;
                        double nextTime = selfcheck.getNextAvailableTime();
                        int maxQ = selfcheck.maxQ();
                        Server newServer = new SelfCheckout(id, available, waitingCustomer, nextTime, maxQ);
                        nextShop = nextShop.replace(newServer);
                    }
                }
                System.out.println(nextShop); //debugging purposes
            }

            Event nextEvent = new DoneEvent(customer, nextServer);
            return Pair.of(nextShop, nextEvent);
        }, server, SERVED);
    }

    /**
     * Returns the string representation.
     */
    @Override  
    public String toString() {
        double timeServed;

        double availableTime = this.getServer().getNextAvailableTime();
        double arrivalTime = this.getCustomer().getArrivalTime();

        if (availableTime > arrivalTime) {
            timeServed = availableTime;
        } else {
            timeServed = arrivalTime;
        }
        return String.format("%.3f %s served by %s", 
                timeServed, this.getCustomer().getId(), this.getServer().getName());
    }
}

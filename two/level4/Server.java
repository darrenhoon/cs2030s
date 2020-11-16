//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private final int identifier;
    private final boolean isAvailable;
    private final boolean hasWaitingCustomer;
    private final double nextAvailableTime;
    private final int maxQ;
    private final List<Customer> cusList;

    /**
     * Server object initialised with 4 parameters.
     * Parameters used to determine the logic of the simulation and
     * occurence of event to come
     */
    public Server(int id, boolean isAvailable, boolean hasWaitingCustomer, double nextTiming) {
        this.identifier = id;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextTiming;
        this.maxQ = 1;
        this.cusList = new ArrayList<Customer>();
    }

    public Server(int id, boolean isAvailable, boolean hasWaitingCustomer, double nextTiming, int maxQ, List<Customer> cusList) {
        this.identifier = id;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextTiming;
        this.maxQ = maxQ;
        this.cusList = cusList;
    }

    @Override
    public String toString() {
        String message = String.format("%d is ",this.identifier);
        String status = "busy; ";
        if (this.isAvailable == false && this.hasWaitingCustomer == false) {
            status += String.format("available at %.3f",this.nextAvailableTime);
        }
        if (this.isAvailable == false && this.hasWaitingCustomer == true) {
            status += String.format("waiting customer to be served at %.3f",this.nextAvailableTime);
        }
        if (this.isAvailable == true) {
            status = "available";
        }
        return message + status;
    }
    
    double nextAvailableTime() {
        return this.nextAvailableTime;
    }
    
    boolean hasWaitingCustomer() {
        return this.maxQ == (this.cusList.size() - 1);
    }

    int identifier() {
        return this.identifier;
    }
    
    boolean isAvailable() {
        return this.isAvailable;
    }

    int maxQ() {
        return this.maxQ;
    }

    List<Customer> cusList() {
        return this.cusList;
    }
}

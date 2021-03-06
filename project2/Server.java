package cs2030.simulator;

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
    private final boolean isResting;
    
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
        if (hasWaitingCustomer == false) {
            this.maxQ  = 1;
        } else {
            this.maxQ = -1;
        }
        this.cusList = new ArrayList<Customer>();
        this.isResting = false;
    }

    /**
     * Server constructor that takes in a list of customers,
     * with first customer in the list being "Served" and index 1
     * onwards being "in queue".
     */
    public Server(int id, boolean isAvailable, boolean hasWaitingCustomer,
            double nextTiming, int maxQ, List<Customer> cusList) {
        this.identifier = id;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextTiming;
        this.maxQ = maxQ;
        this.cusList = cusList;
        this.isResting = false;
    }

    /**
     * Server that has his/her rest status changed due to ServerRest event.
     */
    public Server(int id, boolean isAvailable, boolean hasWaitingCustomer, 
            double nextTiming, int maxQ, List<Customer> cusList, boolean isResting) {
        this.identifier = id;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextTiming;
        this.maxQ = maxQ;
        this.cusList = cusList;
        this.isResting = isResting;
    }


    @Override
    public String toString() {
        String message = String.format("%d is ",this.identifier);
        String status = "busy; ";
        if (this.isAvailable == false && this.hasWaitingCustomer() == false) {
            status += String.format("available at %.3f",this.nextAvailableTime);
        }
        if (this.isAvailable == false && this.hasWaitingCustomer() == true) {
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

    int qcount() {
        if (this.cusList.size() == 0) {
            return 0;
        }
        return this.cusList.size() - 1;
    }

    boolean isResting() {
        return this.isResting;
    }

    Server rest() {
        return new Server(this.identifier, this.isAvailable, this.hasWaitingCustomer,
                this.nextAvailableTime, this.maxQ, this.cusList, true);
    }

    Server back() {
        return new Server(this.identifier, this.isAvailable, this.hasWaitingCustomer,
                this.nextAvailableTime, this.maxQ, this.cusList, false);
    }
}

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Server {
    private final int identifier;
    private final boolean isAvailable;
    private final boolean hasWaitingCustomer;
    private final double nextAvailableTime;

    Server(int identifier, boolean isAvailable, boolean hasWaitingCustomer, double nextAvailableTime) {
        this.identifier = identifier;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextAvailableTime;
    }

    public String toString() {
        String message = String.format("%d is ",this.identifier);
        String status = "is busy; ";
        if (this.isAvailable == false && this.hasWaitingCustomer == false) {
            status += String.format("available at %.3f",this.nextAvailableTime);
        }
        if(this.isAvailable == false && this.hasWaitingCustomer == true) {
            status += String.format("waiting customer to be served at %.3f",this.nextAvailableTime);
        }
        if (this.isAvailable == true) {
            status = "available";
        }
        return message + status;
    }

    boolean canServe() {
        return this.isAvailable;
    }

    Server serveCustomer(Customer c) {
        double nextTime = c.getArrivalTime() + serviceTime;
        return new Server(this.identifier,false,false, nextTime);
    }

    boolean canQueue() {
        return this.hasWaitingCustomer == false;
    }
}

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private final int identifier;
    private final boolean isAvailable;
    private final boolean hasWaitingCustomer;
    private final double nextAvailableTime;

    Server(int id, boolean isAvailable, boolean hasWaitingCustomer, double nextAvailableTime) {
        this.identifier = id;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextAvailableTime;
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

    boolean getAvailability() {
        return this.isAvailable;
    }

    double getAvailableTime() {
        return this.nextAvailableTime;
    }

    boolean canQueue() {
        return this.hasWaitingCustomer == false;
    }

    boolean getHasWaitingCustomer() {
        return this.hasWaitingCustomer;
    }

    int getId() {
        return this.identifier;
    }
}

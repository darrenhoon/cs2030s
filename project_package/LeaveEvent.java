package cs2030.simulator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class LeaveEvent extends Event {
    
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;

    LeaveEvent(Customer customer, List<Server> servers) {
        this.serverList = servers;
        this.customer = customer;
    }

    @Override
    public String toString() {
        double arrivalTime = this.getCustomer().getArrivalTime();
        int c = this.getCustomer().getId();

        String message = String.format("%.3f %d ", arrivalTime, c);
        message += "leaves";
        return message;
    }

    Event execute() {
        return this;
    }

    double getServiceTime() {
        return this.serviceTime;
    }

    Customer getCustomer() {
        return this.customer;
    }

    List<Server> getServerList() {
        return this.serverList;
    }

    double getCurrentTime() {
        return this.getCustomer().getArrivalTime();
    }
}

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class DoneEvent extends Event {
    
    private final Server server; 
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;

    DoneEvent(Customer customer, List<Server> servers, Server server) {
        this.server = server;
        this.customer = customer;
        this.serverList = servers;
    }

    public String toString() {
        double completeTime = this.getCustomer().getArrivalTime() + this.getServiceTime();
        int c = this.getCustomer().getId();
        int s = this.getServer().getId();
        String message = String.format("%.3f %d done serving by %d", completeTime, c, s);
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

    Server getServer() {
        return this.server;
    }
}

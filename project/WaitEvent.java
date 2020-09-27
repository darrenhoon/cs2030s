import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class WaitEvent extends Event {

    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;

    WaitEvent(Customer customer, List<Server> servers, Server s) {
        this.customer = customer;
        this.serverList = servers;
        this.server = s;
    }

    public String toString() {
        int c = this.getCustomer().getId();
        int s = this.getServer().getId();
        double arrivalTime = this.getCustomer().getArrivalTime();
        return String.format("%.3f %d waits to be served by %d",arrivalTime, c, s);
    }

    @Override
    Event execute() {
        double servingTime = this.getServer().getAvailableTime();
        Customer c = new Customer(this.getCustomer().getId(),servingTime);
        return new ServeEvent(c,this.getServerList(),this.getServer());
    }

    Server getServer() {
        return this.server;
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

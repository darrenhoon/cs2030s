package cs2030.simulator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class WaitEvent extends Event {

    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;

    public WaitEvent(Customer customer, List<Server> servers, Server s) {
        this.customer = customer;
        this.serverList = servers;
        this.server = s;
    }

    @Override
    public String toString() {
        int c = this.getCustomer().getId();
        int s = this.getServer().getId();
        double arrivalTime = this.getCustomer().getArrivalTime();
        return String.format("%.3f %d waits to be served by %d", arrivalTime, c, s);
    }

    Event execute() {

        Server currentServer = this.getServer();
        Customer currentCustomer = this.getCustomer();
        
        int index = this.serverList.indexOf(currentServer);
        double newTime = currentServer.getAvailableTime();
        Server newServer = new Server(currentServer.getId(), false, true, newTime);
        this.serverList.set(index, newServer);
        Customer c = new Customer(currentCustomer.getId(), newTime);
        ServeEvent nextEvent = new ServeEvent(c, this.serverList, newServer);
        return nextEvent;
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

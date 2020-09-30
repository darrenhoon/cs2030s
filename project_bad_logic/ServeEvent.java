package cs2030.simulator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ServeEvent extends Event {

    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;
    private final double nextTime;

    public ServeEvent(Customer customer, List<Server> servers, Server s) {
        this.customer = customer;
        this.serverList = servers;
        this.server = s;
        this.nextTime = s.getAvailableTime() + serviceTime;
    }

    @Override
    public String toString() {
        double arrivalTime = this.getCustomer().getArrivalTime();
        int c = this.getCustomer().getId();
        int s = this.getServer().getId();
        String message = String.format("%.3f %d served by %d", arrivalTime, c, s);
        return message;
    }

    Event execute() {
        Server currentServer = this.getServer();
        Customer currentCustomer = this.getCustomer();

        int index = this.serverList.indexOf(currentServer);

        double serveTime = currentCustomer.getArrivalTime() + serviceTime;
        
        Server newServer = new Server(currentServer.getId(), false, false, serveTime);
        
        this.serverList.set(index, newServer);
        DoneEvent nextEvent =  new DoneEvent(currentCustomer, this.serverList, newServer);
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

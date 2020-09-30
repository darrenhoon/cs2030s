import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class WaitEvent extends Event {

    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;

    WaitEvent(Customer customer, List<Server> servers, Server s) {
        this.customer = customer;
        this.serverList = servers;
        this.server = s;
    }

    @Override
    public String toString() {
        int c = this.getCustomer().getId();
        int s = this.getServer().getId();
        double arrivalTime = this.getCustomer().getArrivalTime();
        return String.format("%.3f %d waits to be served by %d",arrivalTime, c, s);
    }

    Event execute() {

        WaitEvent currEvent = (WaitEvent) this;
        Server currentServer = currEvent.getServer();

        Server newServer;

        for (int i = 0; i < this.serverList.size(); i++) {
            Server s = this.serverList.get(i);
            if (s == currentServer) {

                double newTime = s.getAvailableTime();
                newServer = new Server(currentServer.getId(), false, true, newTime);
                this.serverList.set(i, newServer);
                Customer c = new Customer(this.getCustomer().getId(),newTime);
                return new ServeEvent(c,this.serverList, newServer);
            }
        }
        return this;
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

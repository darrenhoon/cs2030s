import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ServeEvent extends Event {

    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;
    private final double nextTime;

    ServeEvent(Customer customer, List<Server> servers, Server s) {
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
        ServeEvent currEvent = (ServeEvent) this;
        Server currentServer = currEvent.getServer();
        Customer currentCustomer = this.customer;
        Server newServer;

        for (int i = 0; i < this.serverList.size(); i++) {
            Server s = this.serverList.get(i);

            double nextAvailableTime = currentCustomer.getArrivalTime() + 1.0;
            if (s == currentServer) {

                double serveTime = currentCustomer.getArrivalTime() + 1.0;

                newServer = new Server(currentServer.getId(), false, false, serveTime);
                this.serverList.set(i, newServer);

                return new DoneEvent(currentCustomer,this.serverList, newServer);
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

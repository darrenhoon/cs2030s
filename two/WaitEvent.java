import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class WaitEvent extends Event {
    
    private static final double SERVICE_TIME = 1.0;

    public WaitEvent(Customer customer, Server s) {
        super(customer, shop -> {
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            double nextTiming = currentServer.nextAvailableTime() + SERVICE_TIME;
            Server nextServer = new Server(currentId, false, false, nextTiming);
            Shop nextShop = shop.replace(nextServer);
            ServeEvent nextEvent = new ServeEvent(customer, nextServer);
            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
            return pair; 
        }, s);
    }

    @Override
    public String toString() {
        int c = this.customer().identifier();
        int s = this.server().identifier();
        double arrivalTime = this.customer().arrivalTime();
        return String.format("%.3f %d waits to be served by %d",arrivalTime, c, s);
    }

    @Override
    public ServeEvent execute() {
        double servingTime = this.getServer().getAvailableTime();
        Customer customer = new Customer(this.getCustomer().getId(),servingTime);
        
        Customer c;
        Customer currentCustomer = this.getCustomer();
        Server newServer;
        Server currentServer = this.getServer();

        for (int i = 0; i < this.serverList.size(); i++) {
            Server s = this.serverList.get(i);
            if (s.equals(currentServer)) {

                double newTime = s.getAvailableTime();

                newServer = new Server(currentServer.getId(), false, true, newTime);
                this.serverList.set(i, newServer);
                c = new Customer(currentCustomer.getId(),newTime);
                ServeEvent nextEvent = new ServeEvent(c,this.serverList, newServer);
        
                return nextEvent;
            }
        }
        return new ServeEvent(customer,this.getServerList(),this.getServer());
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

    double getElapsedTime() {
        Server s = this.getServer();
        double arriveTime = this.getCustomer().getArrivalTime();
        return s.getAvailableTime() - arriveTime;
    }
}

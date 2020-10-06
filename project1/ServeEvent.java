package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ServeEvent extends Event {
    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private final double SERVICE_TIME = 1.0;
    private final double nextTime;

    /**
     * Event after ArriveEvent.
     * @execute used to pass the parameters passed in to return a DoneEvent
     */
    public ServeEvent(Customer customer, List<Server> servers, Server s) {
        this.customer = customer;
        this.serverList = servers;
        this.server = s;
        this.nextTime = s.getAvailableTime() + this.getServiceTime();
    }

    @Override
    public String toString() {

        double time;

        double availableTime = this.getServer().getAvailableTime();
        double arrivalTime = this.getCustomer().getArrivalTime();
        if (availableTime > arrivalTime) {
            time = availableTime;
        } else {
            time = arrivalTime;
        }
        int c = this.getCustomer().getId();
        int s = this.getServer().getId();
        String message = String.format("%.3f %d served by %d", time, c, s);
        return message;
    }

    @Override
    public DoneEvent execute() {
        Customer currentCustomer = this.getCustomer();
        Server currentServer = this.getServer();
        Server s;
        
        for (int i = 0; i < this.serverList.size(); i++) {
            
            Server server = this.serverList.get(i);

            if (server.equals(currentServer)) {
                double arrivalTime = currentCustomer.getArrivalTime();
                double serveTime = arrivalTime + this.getServiceTime();

                s = new Server(currentServer.getId(), false, false, serveTime);
                this.serverList.set(i, s);
                
                DoneEvent nextEvent = new DoneEvent(currentCustomer, this.serverList, s);
        
                return nextEvent;
            }
        }
        DoneEvent nextEvent = new DoneEvent(currentCustomer, this.serverList, currentServer);
        return nextEvent;
    }

    Server getServer() {
        return this.server;
    }

    double getServiceTime() {
        return SERVICE_TIME;
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

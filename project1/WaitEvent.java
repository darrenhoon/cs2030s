package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class WaitEvent extends Event {

    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;

    /**
     * Event after ArriveEvent if all waiters are busy and a waiter has no Waiting Customer.
     */
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

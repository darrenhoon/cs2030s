package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ArriveEvent extends Event {
    private final Customer customer;
    private final List<Server> serverList;
    private final double serviceTime = 1.0;

    public ArriveEvent(Customer customer, List<Server> servers) {
        this.serverList = servers;
        this.customer = customer;
    }

    @Override
    public String toString() {
        double time = this.getCustomer().getArrivalTime();
        int c = this.getCustomer().getId();
        String message = String.format("%.3f %d arrives", time, c);
        return message;
    }

    @Override
    public Event execute() {
        for (int i = 0; i < this.serverList.size(); i++) {
            Server s = this.serverList.get(i);
            if (s.getAvailability()) {
                return new ServeEvent(this.getCustomer(),this.serverList, s);
            }
        }
        for (int i = 0; i < this.serverList.size(); i++) {
            Server s = this.serverList.get(i);
            if (s.canQueue()) {
                return new WaitEvent(this.getCustomer(),this.serverList,s);
            }
        }
        return new LeaveEvent(this.getCustomer(),this.serverList);
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

package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SelfCheckout extends Server {

    private static final List<Customer> queueList = new ArrayList<Customer>();
    private static final List<Customer> servingList = new ArrayList<Customer>();
    private static final List<Customer> servedList = new ArrayList<Customer>();
    private static int counters = 0;
    
    /**
     * Server object initialised with 4 parameters
     * Parameters used to determine the logic of the simulation and
     * occurence of event to come.
     */
    public SelfCheckout(int id, boolean isAvailable, boolean hasWaitingCustomer, double nextTiming,
            int maxQ) {
        super(id, isAvailable, hasWaitingCustomer, nextTiming,
                maxQ, (List<Customer>) new ArrayList<Customer>());
    }

    void stats() {
        System.out.println("Server: " + this.identifier());
        System.out.println("isAvailable: " + this.isAvailable());
        System.out.println("HWC: " + this.hasWaitingCustomer());
        System.out.println("SCQ Len: " + this.queueList().size());
        System.out.println("SCQ: " + this.queueList());
        System.out.println("maxQ: " + this.maxQ());
        System.out.println("ServingQ: " + this.servingList().size());
        System.out.println("ServingQ len: " + this.queueList());
        System.out.println("SCS: " + this.servedList());
        System.out.println("Timing: " + this.nextAvailableTime());
    }

    @Override
    boolean hasWaitingCustomer() {
        return this.maxQ() == this.queueList().size();
    }

    void queueAdd(Customer c) {
        SelfCheckout.queueList.add(c);
    }

    int queueSize() {
        return SelfCheckout.queueList.size();
    }

    void queueRemove(int i) {
        SelfCheckout.queueList.remove(i);
    }
    
    void queueRemove(Customer c) {
        List<Customer> tempList = new ArrayList<Customer>(SelfCheckout.queueList);
        SelfCheckout.queueList.clear();
        tempList.forEach(customer -> {
            if (customer.identifier() != c.identifier()) {
                SelfCheckout.queueList.add(customer);
            }
        });
    }

    Customer queueGet(int i) {
        return SelfCheckout.queueList.get(i);
    }
    
    List<Customer> queueList() {
        return SelfCheckout.queueList;
    }

    void servedAdd(Customer c) {
        SelfCheckout.servedList.add(c);
    }

    List<Customer> servedList() {
        return SelfCheckout.servedList;
    }

    boolean servedCheck(Customer c) { //check if customer has already been served
        for (Customer curCus: SelfCheckout.servedList) {
            if (curCus.identifier() == c.identifier()) {
                return true;
            }
        }
        return false;
    }

    boolean queueCheck(Customer c) { //should return false if person is not in queue anymore
        return SelfCheckout.queueList.stream()
            .anyMatch(customer -> customer.identifier() == c.identifier());
    }

    void servingAdd(Customer c) {
        SelfCheckout.servingList.add(c);
    }

    void servingRemove(Customer c) {
        List<Customer> tempList = new ArrayList<Customer>(SelfCheckout.servingList);
        SelfCheckout.servingList.clear();
        tempList.forEach(customer -> {
            if (customer.identifier() != c.identifier()) {
                SelfCheckout.servingList.add(customer);
            }
        });
    }

    boolean servingCheck(Customer c) { //check if customer is already being served
        for (Customer curCus: SelfCheckout.servingList) {
            if (curCus.identifier() == c.identifier()) {
                return true;
            }
        }
        return false;
    }
    
    List<Customer> servingList() {
        return SelfCheckout.servingList;
    }

    @Override
    int qcount() {
        return SelfCheckout.queueList.size();
    }

    int counters() {
        return SelfCheckout.counters;
    }

    Server addCounter() {
        SelfCheckout.counters += 1;
        return this;
    }
}

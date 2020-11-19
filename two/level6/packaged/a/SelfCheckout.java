package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SelfCheckout extends Server {

    private static final List<Customer> SCQlist = new ArrayList<Customer>();
    private static final List<Customer> serving = new ArrayList<Customer>();
    private static final List<Customer> SCSlist = new ArrayList<Customer>();
    
    /**
     * Server object initialised with 4 parameters.
     * Parameters used to determine the logic of the simulation and
     * occurence of event to come
     */
    public SelfCheckout(int id, boolean isAvailable, boolean hasWaitingCustomer, double nextTiming,
            int maxQ) {
        super(id, isAvailable, hasWaitingCustomer, nextTiming,
                maxQ, (List<Customer>) new ArrayList<Customer>());
    }

    @Override
    boolean hasWaitingCustomer() {
        return this.maxQ() == this.SCQlist().size();
    }

    void SCQadd(Customer c) {
        SelfCheckout.SCQlist.add(c);
    }

    int SCQsize() {
        return SelfCheckout.SCQlist.size();
    }

    void SCQremove(int i) {
        SelfCheckout.SCQlist.remove(i);
    }

    Customer SCQget(int i) {
        return SelfCheckout.SCQlist.get(i);
    }
    
    List<Customer> SCQlist() {
        return SelfCheckout.SCQlist;
    }

    void SCQremove(Customer c) {
        List<Customer> tempList = new ArrayList<Customer>(SelfCheckout.SCQlist);
        SelfCheckout.SCQlist.clear();
        tempList.forEach(customer -> {
            if (customer.identifier() != c.identifier()) {
                SelfCheckout.SCQlist.add(customer);
            }
        });
    }

    void SCSadd(Customer c) {
        SelfCheckout.SCSlist.add(c);
    }

    List<Customer> SCSlist() {
        return SelfCheckout.SCSlist;
    }

    boolean SCScheck(Customer c) { //check if customer has already been served
        for (Customer curCus: SelfCheckout.SCSlist) {
            if (curCus.identifier() == c.identifier()) {
                return true;
            }
        }
        return false;
    }

    boolean SCQcheck(Customer c) { //should return false if person is not in queue anymore
        return SelfCheckout.SCQlist.stream()
            .anyMatch(customer -> customer.identifier() == c.identifier());
    }

    void servingAdd(Customer c) {
        SelfCheckout.serving.add(c);
    }

    void servingRemove(Customer c) {
        List<Customer> tempList = new ArrayList<Customer>(SelfCheckout.serving);
        SelfCheckout.serving.clear();
        tempList.forEach(customer -> {
            if (customer.identifier() != c.identifier()) {
                SelfCheckout.serving.add(customer);
            }
        });
    }

    boolean servingCheck(Customer c) { //check if customer is already being served
        for (Customer curCus: SelfCheckout.serving) {
            if (curCus.identifier() == c.identifier()) {
                return true;
            }
        }
        return false;
    }
    
    List<Customer> servingList() {
        return SelfCheckout.serving;
    }
}

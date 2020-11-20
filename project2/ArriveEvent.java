package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ArriveEvent extends Event {

    /**
     * Every customer gets passed here first to be evaluated against existing shops
     * and servers and next Event to be determined here.
     */
    public ArriveEvent(Customer customer) {
        super(customer, shop -> {

            double arrivalTime = customer.arrivalTime();
            Optional<Server> isAvailable = shop
                .find(x -> x.isAvailable());

            Optional<Server> hasWaitingCustomer = shop
                .find(x -> x.hasWaitingCustomer() == false);

            //serveEvent
            if ((isAvailable.isEmpty() == false)
                    && (arrivalTime >= isAvailable.get().nextAvailableTime())) {
                Server currentServer = isAvailable.get();

                Shop nextShop;
                Customer nextCus;
                if (customer instanceof Greedy) {
                    nextCus = (Customer) new Greedy(
                            customer.identifier(),
                            customer.arrivalTime(),
                            customer.serviceTimeSupplier());
                } else {
                    nextCus = new Customer(
                            customer.identifier(),
                            customer.arrivalTime(),
                            customer.serviceTimeSupplier());
                }

                //if current Server is a self-checkout
                if (currentServer instanceof SelfCheckout) {
                    SelfCheckout after = new SelfCheckout(currentServer.identifier(), false,
                            false, currentServer.nextAvailableTime(),
                            currentServer.maxQ());
                    nextShop = shop.replace((Server) after);
                } else { //current Server is a normal server
                    List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());

                    cusList.add(nextCus);
                    Server after = new Server(currentServer.identifier(), false,
                            false, currentServer.nextAvailableTime(),
                            currentServer.maxQ(), cusList, currentServer.isResting());
                    nextShop = shop.replace(after);
                }

                ServeEvent nextEvent = new ServeEvent(nextCus, currentServer);

                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            } else if (hasWaitingCustomer.isEmpty() == false) { //waitevent 

                boolean isGreedy;
                if (customer instanceof Greedy) {
                    isGreedy = true;
                } else {
                    isGreedy = false;
                }

                Server currentServer;
                //customer is a greedy customer
                if (isGreedy) {
                    currentServer = shop.shortestQ();
                } else {
                    currentServer = hasWaitingCustomer.get();
                }

                Shop nextShop;
                Customer nextCus;
                if (isGreedy) {
                    nextCus = (Customer) new Greedy(
                            customer.identifier(),
                            customer.arrivalTime(),
                            customer.serviceTimeSupplier(), true);
                } else {
                    nextCus = (Customer) new Customer(
                            customer.identifier(),
                            customer.arrivalTime(),
                            customer.serviceTimeSupplier(), true); 
                }

                //if curSer is a selfcheckout
                if (currentServer instanceof SelfCheckout) {

                    SelfCheckout sc = (SelfCheckout) currentServer;
                    int newQlength = sc.queueSize() + 1;

                    //actually can remove since HWC does nothing now 
                    boolean newHWC = (newQlength == currentServer.maxQ());

                    SelfCheckout after = new SelfCheckout(currentServer.identifier(), 
                            false, newHWC, currentServer.nextAvailableTime(),
                            currentServer.maxQ());

                    //customer goes into the static queue
                    after.queueAdd(nextCus);
                    nextShop = shop.replace((Server) after);
                } else { //currentServer is a normalServer
                    List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());

                    cusList.add(nextCus);
                    int newQlength = currentServer.cusList().size() + 1;
                    boolean newHWC = (newQlength == currentServer.maxQ());

                    Server after = new Server(currentServer.identifier(), false, newHWC,
                            currentServer.nextAvailableTime(), currentServer.maxQ(), cusList,
                            currentServer.isResting());
                    nextShop = shop.replace(after);
                }
                WaitEvent nextEvent = new WaitEvent(nextCus, currentServer);
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            } else {
                LeaveEvent nextEvent = new LeaveEvent(customer);
                Pair<Shop, Event> pair = new Pair<Shop, Event>(shop, (Event) nextEvent);
                return pair;
            }
        });
    }

    @Override
    public String toString() {
        double time = this.customer().arrivalTime();
        int c = this.customer().identifier();
        String message;
        if (this.customer() instanceof Greedy) { 
            message = String.format("%.3f %d(greedy) arrives", time, c); 
        } else {
            message = String.format("%.3f %d arrives", time, c);
        }
        return message;
    }
}

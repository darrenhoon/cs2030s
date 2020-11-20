package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ServeEvent extends Event {

    /**
     * ServeEvent, initialized with customer and
     * Server (for ID purposes) and returns and
     * updated shop or null if the ServeEvent
     * has already taken place.
     */
    public ServeEvent(Customer customer, Server s) {
        super(customer, shop -> {

            //current Server's details
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();

            //checking out
            //System.out.println("Server: " + currentId + "\n" + currentServer.cusList() + "\n");

            //if server is human and is resting
            if (!(currentServer instanceof SelfCheckout) && (currentServer.isResting())) {
                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());

                double nextTiming = currentServer.nextAvailableTime();

                Server nextServer = new Server(currentId, false,
                        currentServer.hasWaitingCustomer(), nextTiming, 
                        currentServer.maxQ(), cusList, true);

                Customer nextCus;
                if (customer instanceof Greedy) {
                    nextCus = (Customer) new Greedy(
                            customer.identifier(),
                            customer.arrivalTime(),
                            customer.serviceTimeSupplier(),
                            customer.queued());
                } else {
                    nextCus = new Customer(
                            customer.identifier(),
                            customer.arrivalTime(),
                            customer.serviceTimeSupplier(),
                            customer.queued());
                }

                ServeEvent nextEvent = new ServeEvent(nextCus,
                        nextServer);
                return new Pair<Shop, Event>(shop, (Event) nextEvent);
            }

            Customer nextCus;
            Server nextServer;
            Shop nextShop;

            //System.out.println("\n" + s.nextAvailableTime() + " SE => curCus is: " + customer);
            //System.out.println("SE => curSer is: " + s);



            //if curSER is a SCO
            if (currentServer instanceof SelfCheckout) {
                SelfCheckout sc = (SelfCheckout) currentServer;

                //System.out.println("SE => ============SERVER STATS==================");
                //System.out.println("SE => Current server is: " + currentServer);
                //sc.stats();
                //System.out.println("SE => Current customer is: " + customer.identifier());
                //System.out.println("SE => ============================================");
                //if customer has already been served and left
                //or customer is already being served
                if ((sc.servedCheck(customer)) || (sc.servingCheck(customer))) {
                    //System.out.println("SE => Customer is not to be served again
                    //  or has to be served later: " + customer.identifier() + "\n");


                    boolean isAvailable;
                    double nextTiming = sc.nextAvailableTime();
                    if (sc.queueList().size() != 0) {
                        isAvailable = false;
                    } else {
                        isAvailable = true;
                    }

                    boolean waitingCustomer = (sc.queueList().size() == sc.maxQ());

                    SelfCheckout nextSC = new SelfCheckout(
                            currentId,
                            isAvailable, waitingCustomer, nextTiming, 
                            currentServer.maxQ());
                    nextServer = (Server) nextSC;
                    //is the test6_7 issue here?
                    nextShop = shop.replace(currentServer);
                    return new Pair<Shop, Event>(nextShop, null);
                } else if ((sc.servingList().size() == sc.maxQ())
                        && (customer.queued())
                        && (sc.servingCheck(customer) == false)
                        && (s.nextAvailableTime() != sc.nextAvailableTime())) {
                    //the service counters are all busy now so a new
                    //serve event cannot be made so send the serveevent
                    //back another time again...

                    //System.out.println("SE => Queue is full. Customer affected is: 
                    //  " + customer.identifier());
                    //System.out.println("SE => current Serving are: " + sc.servingList());

                    ServeEvent nextEvent = new ServeEvent(customer,
                            (Server) sc);
                    return new Pair<Shop, Event>(shop, nextEvent);
                } else if ((sc.queueCheck(customer) == true) && 
                        (sc.servingCheck(customer) == false) && 
                        (sc.servedCheck(customer) == false) &&
                        (sc.servingList().size() != sc.counters())) {
                    //customer was in queue, is not being served, has not been served.

                    double serviceTime = customer.serviceTime();
                    double availableTime = currentServer.nextAvailableTime();
                    double arrivalTime = customer.arrivalTime();
                    double currentTime;

                    if (availableTime > arrivalTime) {
                        currentTime = availableTime;
                    } else {
                        currentTime = arrivalTime;
                    }

                    double nextTiming = currentTime + serviceTime;
                    SelfCheckout nextSC = new SelfCheckout(currentId, false,
                            currentServer.hasWaitingCustomer(), nextTiming, 
                            currentServer.maxQ());

                    /*
                       System.out.println("SE => I suspect CUS16 IS TAKEN OUT HERE");
                       System.out.println("SE => SCO counters are: " + sc.counters());
                       System.out.println("SE => customer is: " + customer);
                       System.out.println("SE => before's SCQ: " + sc.SCQlist());
                       System.out.println("SE => before's ServinList: " + sc.servingList());
                       */

                    sc.queueRemove(customer);
                    sc.servingAdd(customer);
                    nextServer = (Server) nextSC;

                    //System.out.println("SE => after's SCQ: " + sc.SCQlist());
                    //System.out.println("SE => after's servingList: " + sc.servingList());


                    nextShop = shop.replace((Server) nextServer);

                    if (customer instanceof Greedy) {
                        nextCus = (Customer) new Greedy(
                                customer.identifier(),
                                nextTiming,
                                customer.serviceTimeSupplier(),
                                customer.queued(), customer.isHijacker());
                    } else {
                        nextCus = new Customer(
                                customer.identifier(),
                                nextTiming,
                                customer.serviceTimeSupplier(),
                                customer.queued(), customer.isHijacker()); 
                    }
                } else if (sc.servingList().size() == sc.counters()) {
                    return new Pair<Shop, Event>(shop, null);
                } else {
                    //customer is directly served by SCO.
                    //System.out.println("SE => curCus being served: "
                    //  + customer.identifier());
                    //System.out.println("SE => current Q list: " + 
                    //  ((SelfCheckout) currentServer).SCQlist());
                    double serviceTime = customer.serviceTime();
                    double availableTime = currentServer.nextAvailableTime();
                    double arrivalTime = customer.arrivalTime();
                    double currentTime;

                    if (availableTime > arrivalTime) {
                        currentTime = availableTime;
                    } else {
                        currentTime = arrivalTime;
                    }

                    double nextTiming = currentTime + serviceTime;
                    SelfCheckout nextSC = new SelfCheckout(currentId, false,
                            currentServer.hasWaitingCustomer(), nextTiming, 
                            currentServer.maxQ());

                    sc.servingAdd(customer);

                    nextServer = (Server) nextSC;

                    nextShop = shop.replace((Server) nextServer);
                    if (customer instanceof Greedy) {
                        nextCus = (Customer) new Greedy(
                                customer.identifier(),
                                nextTiming,
                                customer.serviceTimeSupplier(),
                                customer.queued());
                    } else {
                        nextCus = new Customer(
                                customer.identifier(),
                                nextTiming,
                                customer.serviceTimeSupplier(),
                                customer.queued());
                    }
                }
            } else {
                //if curSer is a normal Server
                //to loop if the timing is still too early due to
                //the server going for a rest and delaying
                //all subsequent queues
                if ((currentServer.cusList().isEmpty() == false) && 
                        (currentServer.cusList().get(0).identifier() != customer.identifier())) {
                    List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                    double nextTiming = currentServer.nextAvailableTime();
                    nextServer = new Server(currentId, false,
                            currentServer.hasWaitingCustomer(), nextTiming, 
                            currentServer.maxQ(), cusList, currentServer.isResting());

                    if (customer instanceof Greedy) {
                        nextCus = (Customer) new Greedy(
                                customer.identifier(),
                                customer.arrivalTime(),
                                customer.serviceTimeSupplier(),
                                customer.queued());
                    } else {
                        nextCus = new Customer(
                                customer.identifier(),
                                customer.arrivalTime(),
                                customer.serviceTimeSupplier(),
                                customer.queued());
                    }
                    ServeEvent nextEvent = new ServeEvent(nextCus, nextServer);
                    return new Pair<Shop, Event>(shop, (Event) nextEvent);
                }

                // if server can serve now && customer is at the front of the queue
                double serviceTime = customer.serviceTime();
                double availableTime = currentServer.nextAvailableTime();
                double arrivalTime = customer.arrivalTime();
                double currentTime;

                if (availableTime > arrivalTime) {
                    currentTime = availableTime;
                } else {
                    currentTime = arrivalTime;
                }

                double nextTiming = currentTime + serviceTime;
                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                nextServer = new Server(currentId, false,
                        currentServer.hasWaitingCustomer(), nextTiming, 
                        currentServer.maxQ(), cusList);
                nextShop = shop.replace(nextServer);

                if (customer instanceof Greedy) {
                    nextCus = (Customer) new Greedy(
                            customer.identifier(),
                            nextTiming,
                            customer.serviceTimeSupplier(),
                            customer.queued());
                } else {
                    nextCus = new Customer(
                            customer.identifier(),
                            nextTiming,
                            customer.serviceTimeSupplier(),
                            customer.queued());

                }
            }

            DoneEvent nextEvent = new DoneEvent(nextCus, nextServer);
            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
            return pair; 
        }, s);
    }

    @Override
    public String toString() {

        double time;

        double availableTime = this.server().nextAvailableTime();
        double arrivalTime = this.customer().arrivalTime();

        if (availableTime > arrivalTime) {
            time = availableTime;
        } else {
            time = arrivalTime;
        }
        int c = this.customer().identifier();
        int s = this.server().identifier();

        String message;
        if (this.customer() instanceof Greedy) {
            message = String
                .format("%.3f %d(greedy) served by ", time, c);
        } else {
            message = String
                .format("%.3f %d served by ", time, c);
        }

        if (this.server() instanceof SelfCheckout) {
            message += String.format("self-check %d", s);
        } else {
            message += String.format("server %d", s);
        }
        return message;
    }
}

//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ServeEvent extends Event {

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

                Customer nextCus = new Customer(customer.identifier(),
                        customer.arrivalTime(), customer.serviceTimeSupplier(), customer.queued());

                ServeEvent nextEvent = new ServeEvent(nextCus, nextServer);

                return new Pair<Shop, Event>(shop, (Event) nextEvent);
            }

            Customer nextCus;
            Server nextServer;
            Shop nextShop;

            //if curSER is a SCO
            if (currentServer instanceof SelfCheckout) {
                SelfCheckout sc = (SelfCheckout) currentServer;

                //if customer has already been served by another SCO and has left the place
                if ((sc.SCScheck(customer))) {
                    
                    boolean isAvailable;
                    double nextTiming = sc.nextAvailableTime();
                    if (sc.SCQlist().size() != 0) {
                        isAvailable = false;
                    }
                    else {
                        isAvailable = true;
                    }

                    SelfCheckout nextSC = new SelfCheckout(currentId, isAvailable,
                            currentServer.hasWaitingCustomer(), nextTiming, 
                            currentServer.maxQ());
                    nextServer = (Server) nextSC;
                    
                    if (isAvailable == false) {
                        nextCus = sc.SCQget(0);
                    }
                    else {
                        //need to do something about this because technically
                        //the customer has already been served
                        //handle this at Simulation's serveevent instance level
                        nextCus = null;
                       }
                    
                    nextShop = shop.replace(nextServer);

                    if (nextCus == null) {
                        return new Pair<Shop, Event>(nextShop, null);

                    }
                    
                    else {
                        DoneEvent nextEvent = new DoneEvent(nextCus, nextServer);
                        Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                        return pair; 
 
                    }   
                }
               
                //customer was in queue, is not being served, has not been served.
                else if ((sc.SCQcheck(customer) == true) && (sc.servingCheck(customer) == false) &&(sc.SCScheck(customer) == false)) {
                    
                    double SERVICE_TIME = customer.serviceTime();
                    double availableTime = currentServer.nextAvailableTime();
                    double arrivalTime = customer.arrivalTime();
                    double currentTime;

                    if (availableTime > arrivalTime) {
                        currentTime = availableTime;
                    } else {
                        currentTime = arrivalTime;
                    }

                    double nextTiming = currentTime + SERVICE_TIME;
                    SelfCheckout nextSC = new SelfCheckout(currentId, false,
                            currentServer.hasWaitingCustomer(), nextTiming, 
                            currentServer.maxQ());

                    sc.SCQremove(customer);
                    sc.servingAdd(customer);
                    nextServer = (Server) nextSC;

                    nextShop = shop.replace((Server) nextServer);
                    nextCus = new Customer(customer.identifier(), nextTiming,
                            customer.serviceTimeSupplier(), customer.queued());
                
                }

                //customer is directly served by SCO or waitevent
                else {
                    /*
                    //to loop
                    if ((sc.SCsize() != 0) && 
                    (sc.SCget(0).identifier() != customer.identifier())) {

                    SelfCheckout nextSC = new SelfCheckout(currentId, false,
                    currentServer.hasWaitingCustomer(), currentServer.nextAvailableTime(),
                    currentServer.maxQ());

                    nextServer = (Server) nextSC;

                    ServeEvent nextEvent = new ServeEvent(customer, (Server) nextServer);
                    return new Pair<Shop, Event>(shop, (Event) nextEvent);
                    }
                    */

                    // if SCO can serve now && customer has not been served by another SCO
                    double SERVICE_TIME = customer.serviceTime();
                    double availableTime = currentServer.nextAvailableTime();
                    double arrivalTime = customer.arrivalTime();
                    double currentTime;

                    if (availableTime > arrivalTime) {
                        currentTime = availableTime;
                    } else {
                        currentTime = arrivalTime;
                    }

                    double nextTiming = currentTime + SERVICE_TIME;
                    SelfCheckout nextSC = new SelfCheckout(currentId, false,
                            currentServer.hasWaitingCustomer(), nextTiming, 
                            currentServer.maxQ());
                    
                    //System.out.println("SE => SCO's before SCQlist: " + nextSC.SCQlist());
                    //System.out.println("SE => SCO's before SCSlist: " + nextSC.SCSlist()); 
                    //remove customer from the SCO's queuelist
                    ////System.out.println("SE => SCO's after SCQlist: " + nextSC.SCQlist());
                    //System.out.println("SE => SCO's after SCSlist: " + nextSC.SCSlist()); 

                    sc.SCQremove(customer);
                    sc.servingAdd(customer);
                   
                    nextServer = (Server) nextSC;

                    nextShop = shop.replace((Server) nextServer);
                    nextCus = new Customer(customer.identifier(), nextTiming,
                            customer.serviceTimeSupplier(), customer.queued());
                }
            }





            //if curSer is a normal Server
            else {
                //to loop
                if ((currentServer.cusList().isEmpty() == false) && 
                        (currentServer.cusList().get(0).identifier() != customer.identifier())) {
                    List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                    double nextTiming = currentServer.nextAvailableTime();
                    nextServer = new Server(currentId, false,
                            currentServer.hasWaitingCustomer(), nextTiming, 
                            currentServer.maxQ(), cusList, currentServer.isResting());
                    nextCus = new Customer(customer.identifier(), customer.arrivalTime(),
                            customer.serviceTimeSupplier(), customer.queued());
                    ServeEvent nextEvent = new ServeEvent(nextCus, nextServer);
                    return new Pair<Shop, Event>(shop, (Event) nextEvent);
                        }

                // if server can serve now && customer is at the front of the queue
                double SERVICE_TIME = customer.serviceTime();
                double availableTime = currentServer.nextAvailableTime();
                double arrivalTime = customer.arrivalTime();
                double currentTime;

                if (availableTime > arrivalTime) {
                    currentTime = availableTime;
                } else {
                    currentTime = arrivalTime;
                }

                double nextTiming = currentTime + SERVICE_TIME;
                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                nextServer = new Server(currentId, false,
                        currentServer.hasWaitingCustomer(), nextTiming, 
                        currentServer.maxQ(), cusList);
                nextShop = shop.replace(nextServer);
                nextCus = new Customer(customer.identifier(), nextTiming,
                        customer.serviceTimeSupplier(), customer.queued());
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

        String message = String.format("%.3f %d served by ", time, c);

        if (this.server() instanceof SelfCheckout) {
            message += String.format("self-check %d", s);
        } else {
            message += String.format("server %d", s);
        }
        return message;
    }
}

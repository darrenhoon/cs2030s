package cs2030.simulator;

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
              
                //if customer has already been served and left
                //or customer is already being served
                if ((sc.SCScheck(customer)) || (sc.servingCheck(customer))) {
                    //System.out.println("SE => Customer is not to be served again: " + customer.identifier());
                    
                    boolean isAvailable;
                    double nextTiming = sc.nextAvailableTime();
                    if (sc.SCQlist().size() != 0) {
                        isAvailable = false;
                    }
                    else {
                        isAvailable = true;
                    }

                    boolean HWC = (sc.SCQlist().size() == sc.maxQ());

                    SelfCheckout nextSC = new SelfCheckout(currentId,
                            isAvailable, HWC, nextTiming, 
                            currentServer.maxQ());
                    nextServer = (Server) nextSC;
                   
                    //check if there are anyone else in the Q to
                    //serve
                    if (isAvailable == false) {
                        nextCus = sc.SCQget(0);
                    }
                    else {
                        nextCus = null;
                    }

                    nextShop = shop.replace(nextServer);
                    return new Pair<Shop, Event>(nextShop, null);
                    /*
                    if (nextCus == null) {
                        return new Pair<Shop, Event>(nextShop, null);
                    }
                    else {
                        DoneEvent nextEvent = new DoneEvent(nextCus, nextServer);
                        Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                        return pair;
                    }
                    */
                }

                //the service counters are all busy now so a new
                //serve event cannot be made so send the serveevent
                //back another time again...
                else if (sc.servingList().size() == sc.maxQ()) { 
                    ServeEvent nextEvent = new ServeEvent(customer,
                        (Server) sc);
                return new Pair<Shop, Event>(shop, nextEvent);
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

                //customer is directly served by SCO
                else {
                   
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
                   
                    sc.servingAdd(customer);
                   
                    nextServer = (Server) nextSC;

                    nextShop = shop.replace((Server) nextServer);
                    nextCus = new Customer(customer.identifier(), nextTiming,
                            customer.serviceTimeSupplier(), customer.queued());
                }
            }


            //if curSer is a normal Server
            else {
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

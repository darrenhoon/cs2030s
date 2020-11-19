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
                
                if (sc.SCScheck(customer) || sc.servingCheck(customer)) {
                    return new Pair<Shop, Event>(shop, null);
                }
                
                //customer is directly served by SCO or waitevent
                else {
                    // if SCO can serve now && customer has not been served by another SCO
                    double SERVICE_TIME = customer.serviceTime();
                    double availableTime = currentServer.nextAvailableTime();
                    double arrivalTime = customer.arrivalTime();
                    double currentTime;
                    if (sc.SCQcheck(customer)) {
                        sc.SCQremove(customer);
                    }

                    sc.servingAdd(customer);
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
                    nextServer = (Server) nextSC;

                    nextShop = shop.replace((Server) nextServer);
                    nextCus = new Customer(customer.identifier(),
                            customer.arrivalTime(),
                            customer.serviceTimeSupplier(),
                            customer.queued());
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
                nextCus = new Customer(customer.identifier(),
                        customer.arrivalTime(),
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

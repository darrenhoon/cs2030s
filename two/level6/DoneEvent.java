package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DoneEvent extends Event {

    public DoneEvent(Customer customer, Server s) {
        super(customer, shop -> {

            //server's details
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();

            Shop nextShop;

            //if curSer is a SCO
            if (currentServer instanceof SelfCheckout) {
                
                boolean isAvailable;

                SelfCheckout sc = (SelfCheckout) currentServer;

                sc.servingRemove(customer);
                sc.SCSadd(customer);

                //self checkout counter not the same as entire list
                //System.out.println("Self-checkout's current qlist: " + beforeList);
                if (sc.SCQlist().size() == 0) {
                    isAvailable = true;
                } else {
                    isAvailable = false;
                }
                //System.out.println("Self-checkout's after qlist: " + sc.SClist());
                
                SelfCheckout nextServer = new SelfCheckout(currentId, isAvailable, false,
                        currentServer.nextAvailableTime(), currentServer.maxQ());
                nextShop = shop.replace((Server) nextServer);

                
                /*
                 * @Causes cyclical error
                 * 
                 * /
                //if there is a person in queue, need to return a serveevent
                //may need to KIV this logic against SERVE EVENT (Private test cases)
                /*
                if ((isAvailable == false)) {
                    
                
                    //System.out.println("DE => SCO's before SCQlist: " + sc.SCQlist());
                    //System.out.println("DE => SCO's before SCSlist: " + sc.SCSlist()); 
    
                    Customer nextCus = sc.SCQget(0);
                    //System.out.println("DE => there is a SE next and the Cus is: " + nextCus.identifier());
                    sc.SCQremove(nextCus);
                    sc.servingAdd(nextCus);

                    //System.out.println("DE => SCO's after SCQlist: " + sc.SCQlist());
                    //System.out.println("DE => SCO's after SCSlist: " + sc.SCSlist()); 
                    //System.out.println("DE => next SE's timing" + nextServer.nextAvailableTime());
                    //System.out.println("DE => next SE's CuS" + nextCus.identifier());
                    
                    ServeEvent nextEvent = new ServeEvent(nextCus, nextServer);

                    //System.out.println("DE => hypothetically, the next Event is: " + nextEvent);
 
                    Pair<Shop, Event> pair = new Pair<Shop,Event>(nextShop, (Event) nextEvent);
                    return pair;
                }
                */

                //else, return null. but that means Simulation will also need to
                //acc for this new situation
            }



            //if curSER is a normal Server
            else {
                List<Customer> beforeList = new ArrayList<Customer>(currentServer.cusList());
                List<Customer> afterList;

                //System.out.println("DoneEvent, currently server is: " + currentId);
                //System.out.println("Before edit, Que is: " + beforeList);

                afterList = new ArrayList<Customer>(beforeList);
                boolean isAvailable;

                if (beforeList.size() == 1) {
                    isAvailable = true;
                } else {
                    isAvailable = false;
                }
                afterList.remove(0);
                Server nextServer = new Server(currentId, isAvailable, false,
                        currentServer.nextAvailableTime(), currentServer.maxQ(), afterList);
                nextShop = shop.replace(nextServer);
            }
            
            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, null);    
            return pair;
        }, s);
    }

    public String toString() {
        double completeTime = this.server().nextAvailableTime();
        int c = this.customer().identifier();
        int s = this.server().identifier();
        String message = String.format("%.3f %d done serving by ", completeTime, c);

        if (this.server() instanceof SelfCheckout) {
            message += String.format("self-check %d", s);
        } else {
            message += String.format("server %d", s);
        }        
        return message; 
    }
}

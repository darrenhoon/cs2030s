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
                nextShop = shop;
            }



            //if curSER is a normal Server
            else {
                List<Customer> beforeList = new ArrayList<Customer>(currentServer.cusList());
                List<Customer> afterList;
                
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

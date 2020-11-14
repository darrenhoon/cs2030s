import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DoneEvent extends Event {

    private static final double SERVICE_TIME = 1.0;

    public DoneEvent(Customer customer, Server s) {
        super(customer, shop -> {
            
            int currentId = s.identifier();

            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            
            double nextTiming = currentServer.nextAvailableTime();

            if (currentServer.hasWaitingCustomer() == true) {
                Server nextServer = new Server(currentId, false, false, nextTiming);
                Shop nextShop = shop.replace(nextServer);
            } else {
                Server nextServer = new Server(currentId, true, false, nextTiming);
                Shop nextShop = shop.replace(nextServer);
            }
            return null;
        }, s);
    }

    @Override
    public String toString() {
        double completeTime = this.server().nextAvailableTime();
        int c = this.customer().identifier();
        int s = this.server().identifier();
        String message = String.format("%.3f %d done serving by %d", completeTime, c, s);
        return message; 
    }
}

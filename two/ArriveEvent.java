import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ArriveEvent extends Event {
    private static final double SERVICE_TIME = 1.0;

    public ArriveEvent(Customer customer) {
        super(customer, shop -> {
            double arrivalTime = customer.arrivalTime();
            Optional<Server> serverOptional = shop.find(x -> x.isAvailable());
            if ((serverOptional.isEmpty() == false) && (arrivalTime >= serverOptional.get().nextAvailableTime())) {
                Server before = serverOptional.get();
                double nextAvailableTime = before.nextAvailableTime() + SERVICE_TIME;
                Server after = new Server(before.identifier(), false, false, nextAvailableTime);
                Shop nextShop = shop.replace(after);
                ServeEvent nextEvent = new ServeEvent(customer, before);
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            }
            else if ((serverOptional.isEmpty() == false) && (serverOptional.get().hasWaitingCustomer() == false)) {
                Server before = serverOptional.get();
                double nextAvailableTime = before.nextAvailableTime();
                Server after = new Server(before.identifier(), false, true, nextAvailableTime);
                Shop nextShop = shop.replace(after);
                WaitEvent nextEvent = new WaitEvent(customer, before);
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            }
            else {
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
        String message = String.format("%.3f %d arrives", time, c);
        return message;
    }
}

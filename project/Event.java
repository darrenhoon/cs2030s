import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

abstract class Event {
    abstract Event execute();
    abstract double getServiceTime();
    abstract Customer getCustomer();
    abstract List<Server> getServerList();
    abstract double getCurrentTime();
}

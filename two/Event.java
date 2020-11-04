package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Event {
    abstract Event execute();
    
    abstract double getServiceTime();
    
    abstract Customer getCustomer();
    
    abstract List<Server> getServerList();
    
    abstract double getCurrentTime();
}

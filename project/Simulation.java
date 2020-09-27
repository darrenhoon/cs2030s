import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Simulator {

    public class Simulator{
        private final List<Server> serverList;
        private final List<Customer> customerList;
        private final PriorityQueue<Event> queue;
        private int customersLeft = 0;

        Simulator(List<Customer> customerList, List<Server> serverList) {
            this.customerList = customerList; 
            this.serverList = serverList;

            EventComparator<Event> comp = new EventComparator<Event>();
            this.queue = new PriorityQueue<Event>(this.customerList.size(), comp);
       }
    }
}

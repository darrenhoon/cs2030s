import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Simulation {

    private final List<Server> serverList;
    private final List<Customer> customerList;
    private final Queue<Event> queue;

    Simulation(List<Customer> customerList, List<Server> serverList) {
        this.customerList = customerList; 
        this.serverList = serverList;
        EventComparator eventComp = new EventComparator();
        this.queue = new PriorityQueue<Event>(eventComp);

        for (Customer c: this.customerList) {
            this.queue.add(new ArriveEvent(c,this.serverList));
        }
    }

    void simulate() {
        double totalWaitTime = 0.00;
        int customersServed = 0;
        int customersLeft = 0;


        while (this.queue.isEmpty() == false) {

            Event event = this.queue.poll();
            System.out.println(event.toString());
            Customer currentCustomer = event.getCustomer();

            if (event instanceof LeaveEvent) {
                customersLeft++;
                continue;
            }

            if (event instanceof DoneEvent) {
                customersServed++;
                continue;
            }

            if (event instanceof WaitEvent) {

                WaitEvent currEvent = (WaitEvent) event;
                Server currentServer = currEvent.getServer();

                double newTime = currentServer.getAvailableTime();

                double elapsedTime = newTime - currentCustomer.getArrivalTime();
                totalWaitTime += elapsedTime;

                Event nextEvent = event.execute();
                this.queue.add(nextEvent);
                continue;
            } else {
                Event nextEvent = event.execute();
                this.queue.add(nextEvent);
            }
        }

        double averageTime = totalWaitTime / customersServed;
        String message = String.format("[%.3f %d %d]", averageTime, customersServed, customersLeft);
        System.out.println(message);
    }
}

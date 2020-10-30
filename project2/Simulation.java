package cs2030.simulator;

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

    /**
     * initialised with 2 Lists passed from Main.java.
     *
     * @param customerList is the list of customers
     * @param serverList is the list of servers
     */
    public Simulation(List<Customer> customerList, List<Server> serverList) {
        this.customerList = customerList; 
        this.serverList = serverList;
        EventComparator eventComp = new EventComparator();
        this.queue = new PriorityQueue<Event>(eventComp);

        for (Customer c: this.customerList) {
            this.queue.add(new ArriveEvent(c,this.serverList));
        }
    }

    /**
     * tallies values to be passed into Statistics class for calculation.
     */
    public void simulate() {
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
                Event nextEvent = event.execute();
                customersServed++;
                continue;
            }
            
            if (event instanceof WaitEvent) {
                WaitEvent currEvent = (WaitEvent) event;
                double elapsedTime = currEvent.getElapsedTime();
                totalWaitTime += elapsedTime;
                Event nextEvent = event.execute();
                this.queue.add(nextEvent);
                continue;
            } else {
                Event nextEvent = event.execute();
                this.queue.add(nextEvent);
            }
        }
        Statistics stats = new Statistics(totalWaitTime, customersServed, customersLeft);
        System.out.println(stats.toString());
    }
}

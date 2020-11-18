//package cs2030.simulator;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the simulation.
 */
public class Simulator {
    private final Shop shop;
    private final List<Customer> customers;
    private final Queue<Event> queue;

    /**
     * Creates a simulator that creates a schedule of events.
     * @param customers a list of customers
     * @param servers   a list of servers
     */
    public Simulator(Shop shop, int seedValue, int numOfCustomers, 
            double arrivalRate, double serviceRate, double restingRate, double restingProbability) {

        RandomGenerator rg = new RandomGenerator(seedValue, arrivalRate, serviceRate, restingRate);

        Supplier<Double> arrivalTime = () -> rg.genInterArrivalTime();
        Supplier<Double> serviceTime = () -> rg.genServiceTime();
        Supplier<Double> randomRest = () -> rg.genRandomRest();
        Supplier<Double> restPeriod = () -> rg.genRestPeriod();
        Supplier<Double> customerType = () -> rg.genCustomerType();

        //set random generated suppliers for events
        Event.setServiceTime(serviceTime);
        Event.setRestPeriod(restPeriod);
        Event.setRandomRest(randomRest);
        Event.setCustomerType(customerType);

        //set server's resting probability
        Server.setRestingProb(restingProbability);

        List<Customer> customerList = new ArrayList<Customer>();
        
        //Customer arrival time creation
        double timeArrived = 0;

        //Creation of arrival event according to random customer arrival times
        for (int id = 1; id <= numOfCustomers; id++) {
            customerList.add(new Customer(id, timeArrived));
            timeArrived += arrivalTime.get();
        }
        
        //properties of simulator
        this.shop = shop;
        this.customers = customerList;
        EventComparator eventComp = new EventComparator();
        this.queue = new PriorityQueue<Event>(eventComp);

        //Add arrival event into queue
        customerList.stream().forEachOrdered(customer -> this.queue.add(new ArriveEvent(customer)));
    }

    /**
     * Creates a schedule of events based on the arrival times of customers.
     */
    public void simulate() {
        double totalWaitingTime = 0;
        int customersServed = 0;
        int customersLeft = 0; 
        Shop previousShop = this.shop;

        while (!this.queue.isEmpty()) {
            
            Event event = this.queue.poll();
            Customer currentCustomer = event.getCustomer();
            Server currentServer = event.getServer();

            //Execute next event based on updated shop
            Pair<Shop, Event> pair = event.execute(previousShop);
            Shop nextShop  = pair.first();
            previousShop = nextShop;
            Event nextEvent = pair.second();
            Optional<Event> nextEventOptional = Optional.ofNullable(nextEvent);
            
            if (event instanceof ArriveEvent) {
                // System.out.println(event);
                this.queue.add(nextEvent);
            } else if (event instanceof WaitEvent) {
                this.queue.add(nextEvent);
                // System.out.println(event);
            } else if (event instanceof LeaveEvent) {
                customersLeft++;

                // System.out.println(event);
            } else if (event instanceof ServeEvent) {
                double availableTime = currentServer.getNextAvailableTime();
                double arrivalTime = currentCustomer.getArrivalTime();
                double currentTime;

                if (availableTime > arrivalTime) {
                    currentTime = availableTime;
                } else {
                    currentTime = arrivalTime;
                }

                //calculate customer's waiting time
                double waitingTime = currentTime - currentCustomer.getArrivalTime();
                totalWaitingTime += waitingTime;
                this.queue.add(nextEvent);

                // System.out.println(event);
            } else if (event instanceof DoneEvent) {
                if (nextEventOptional.isPresent()) {
                    this.queue.add(nextEventOptional.get());
                }
                customersServed++;

                // System.out.println(event);
            } else if (event instanceof ServerBack) {
                continue;
            } else {
                this.queue.add(nextEvent);
            }
            System.out.println(event);
        }

        Statistics stats = new Statistics(totalWaitingTime, customersServed, customersLeft);
        System.out.println(stats);
    }
}

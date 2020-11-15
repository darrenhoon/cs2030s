//package cs2030.simulator;



import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

    private final Shop shop;
    private final List<Customer> customerList;
    private final Queue<Event> queue;

    /**
     * initialised with 2 Lists passed from Main.java.
     *
     * @param customerList is the list of customers
     * @param serverList is the list of servers
     */
    public Simulation(Shop shop, int seedValue, int numOfCustomers, double arrivalRate, double serviceRate, double restRate) {

        RandomGenerator rg = new RandomGenerator(seedValue, arrivalRate, serviceRate, restRate);

        //Customer's suppliers
        Supplier<Double> arrivalTime = () -> rg.genInterArrivalTime();
        Supplier<Double> firstCustomerArrivalTime = () -> 0.0;

        //ServiceTime
        Supplier<Double> serviceTime = () -> rg.genServiceTime();

        /*
           Supplier<Double> randomRestStream = () -> rg.genRandomRest();
           Supplier<Double> restPeriodStream = () -> rg.genRestPeriod();
           Supplier<Double> customerTypeStream = () -> rg.genCustomerType();
           */


        //generate customers, first customer having Id = 1
        int customerId = 1;
        Customer firstCustomer = new Customer(customerId, firstCustomerArrivalTime, serviceTime);
        
           List<Customer> remainingCustomers = Stream.iterate(customerId + 1, x -> x + 1)
           .limit(numOfCustomers - 1)
           .map(id -> new Customer(id, arrivalTime, serviceTime))
           .collect(Collectors.toList());
           
        List<Customer> tempList = new ArrayList<Customer>();

        tempList.add(firstCustomer);
        tempList.addAll(remainingCustomers);

        this.customerList = tempList; 
        this.shop = shop;
        EventComparator eventComp = new EventComparator();
        this.queue = new PriorityQueue<Event>(eventComp);

        tempList.stream().forEachOrdered(customer -> this.queue.add(new ArriveEvent(customer)));
    }

    /**
     * tallies values to be passed into Statistics class for calculation.
     */
    public void simulate() {

        double totalWaitTime = 0.00;
        int customersServed = 0;
        int customersLeft = 0;
        Shop previousShop = this.shop;

        //System.out.println(this.queue); //Causes the queue to be eagarly evaluated (do not)

        while (this.queue.isEmpty() == false) {

            Event event = this.queue.poll();

            System.out.println(event.toString());

            Customer currentCustomer = event.customer();
            Server currentServer = event.server();

            Pair<Shop, Event> pair = event.execute(previousShop);
            Shop nextShop  = pair.first();
            previousShop = nextShop;
            Event nextEvent = pair.second();

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

                double elapsedTime = currentServer.nextAvailableTime() - currentCustomer.arrivalTime();
                totalWaitTime += elapsedTime;
                this.queue.add(nextEvent);
                continue;
            } else { //ServeEvent
                this.queue.add(nextEvent);
            }
        }
        Statistics stats = new Statistics(totalWaitTime, customersServed, customersLeft);
        System.out.println(stats.toString());
    }
}

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
import java.util.Optional;

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

        //ServiceTime
        Supplier<Double> serviceTime = () -> rg.genServiceTime();

        /*
           Supplier<Double> randomRestStream = () -> rg.genRandomRest();
           Supplier<Double> restPeriodStream = () -> rg.genRestPeriod();
           Supplier<Double> customerTypeStream = () -> rg.genCustomerType();
           */


        //generate customers
        List<Customer> tempList = new ArrayList<Customer>();

        double previousValue = 0.0;
        for (int id = 1; id <= numOfCustomers; id++) {
            tempList.add(new Customer(id, previousValue, serviceTime));
            previousValue += arrivalTime.get();
        }

        //tempList.forEach(x -> System.out.println(x.arrivalTime())); ok, passed

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
        Shop latestShop = this.shop;

        while (this.queue.isEmpty() == false) {

            Event event = this.queue.poll();

            Customer currentCustomer = event.customer();
            Server previousServer = event.server();

            Pair<Shop, Event> pair = event.execute(latestShop);
            Shop nextShop  = pair.first();

            Event nextEvent = pair.second();

            if ((event instanceof ServeEvent) && (latestShop != this.shop)) {
                Server currentServer = latestShop.find(server -> server.identifier() == previousServer.identifier()).get();

                //System.out.println("Current Event is: " + event);
                //System.out.println("Current Server's qList is: " + currentServer.cusList());
                
                if ((currentServer.cusList().size() != 0) && (currentServer.cusList().get(0).identifier() != currentCustomer.identifier())) {
                    this.queue.add(nextEvent);
                    latestShop = nextShop;
                    continue;
                } else {
                    System.out.println(event.toString());
                    if (currentCustomer.queued()) {
                        
                        double elapsedTime = currentServer.nextAvailableTime() - currentCustomer.arrivalTime();
                        totalWaitTime += elapsedTime;
                    }
                    this.queue.add(nextEvent);
                    latestShop = nextShop;
                    continue;
                }
 
            }
            
            latestShop = nextShop;        
            System.out.println(event.toString());

            if (event instanceof LeaveEvent) {
                customersLeft++;
                continue;
            }

            if (event instanceof DoneEvent) {
                customersServed++;
                continue;
            }

            if (event instanceof WaitEvent) {
                //WaitEvent currEvent = (WaitEvent) event;
                //Server currentServer = nextEvent.server();
                //double elapsedTime = currentServer.nextAvailableTime() - currentCustomer.arrivalTime();
                //totalWaitTime += elapsedTime;
                this.queue.add(nextEvent);

            } else {
                this.queue.add(nextEvent);
            }
        }
        Statistics stats = new Statistics(totalWaitTime, customersServed, customersLeft);
        System.out.println(stats.toString());
    }
}

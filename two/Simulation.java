package cs2030.simulator;

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
    private final double restProb;
    private final Supplier<Double> restGen;
    private final Supplier<Double> restPeriod;

    /**
     * initialised with 10 variables from Main.java which some are
     * to be passed into the RandomGenerator accordingly.
     * @param seedValue is the seedValue for RandomGenerator
     * @param numOfServers is the number of human servers
     * @param selfCheckout is the number of Self-Checkout counters
     * @param maxQ is the max number of customers allowed for any queue
     * @param numOfCustomers is the num of customers in the sim
     * @param arrivalRate is the arrival rate of the Customers
     * @param serviceRate is the service rate of all types of servers
     * @param restRate is the rate at which human servers rest
     * @param restProb is the probability of a human server resting
     * @param greedyProb is the prob of customer being Greedy
     */
    public Simulation(int seedValue, int numOfServers, int selfCheckout,
            int maxQ, int numOfCustomers, double arrivalRate,
            double serviceRate, double restRate, double restProb,
            double greedyProb) {

        RandomGenerator rg = new RandomGenerator(seedValue, arrivalRate, serviceRate, restRate);

        //Customer's suppliers
        Supplier<Double> arrivalTime = () -> rg.genInterArrivalTime();

        //ServiceTime
        Supplier<Double> serviceTime = () -> rg.genServiceTime();

        //generate customers
        List<Customer> tempList = new ArrayList<Customer>();

        //generate Greedy customers;
        Supplier<Double> greedyGen = () -> rg.genCustomerType();

        double previousValue = 0.0;
        for (int id = 1; id <= numOfCustomers; id++) {
            double greedyValue = greedyGen.get();
            if (greedyValue < greedyProb) {
                tempList.add(new Greedy(id, previousValue, serviceTime));
            } else {
                tempList.add(new Customer(id, previousValue, serviceTime));
            }
            previousValue += arrivalTime.get();
        }

        this.customerList = tempList; 

        //generate servers
        int serverId = 1;
        double startTime = 0.0;
        List<Server> serverList = Stream.iterate(serverId, x -> x + 1)
            .limit(numOfServers + selfCheckout)
            .map(id -> {
                if (id <= numOfServers) {
                    return new Server(id, true, false, startTime, maxQ,
                            (List<Customer>) new ArrayList<Customer>());
                } else {
                    return (Server) new SelfCheckout(id, true, false, startTime, maxQ).addCounter();
                }
            })
            .collect(Collectors.toList());

        this.shop = new Shop(serverList);

        EventComparator eventComp = new EventComparator();
        this.queue = new PriorityQueue<Event>(eventComp);
        this.restProb = restProb;
        this.restGen = () -> rg.genRandomRest();
        this.restPeriod = () -> rg.genRestPeriod();

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

                //Current Server with the updated status
                Server currentServer = latestShop
                    .find(server -> server.identifier() == previousServer.identifier()).get();

                //System.out.println("Current Event is: " + event);
                //System.out.println("Current Server's qList is: " + currentServer.cusList());

                //if currentServer is a SCO
                if (currentServer instanceof SelfCheckout) {
                    SelfCheckout sc = (SelfCheckout) currentServer;

                    if (sc.servedCheck(currentCustomer)) {
                        continue;
                    }
                    
                    if (nextEvent == null) {
                        continue;
                    }

                    if ((previousServer.nextAvailableTime() != currentServer.nextAvailableTime())) {
                        continue;
                    } else {
                        System.out.println(event.toString());
                        if (currentCustomer.queued()) {
                            double elapsedTime = currentServer.nextAvailableTime() 
                                -  currentCustomer.arrivalTime();
                            totalWaitTime += elapsedTime;
                        }
                    }
                    this.queue.add(nextEvent);
                    latestShop = nextShop;
                    continue;
 
                } else { //current server is a normal server
                    if ((previousServer.nextAvailableTime() != currentServer.nextAvailableTime())) {
                        this.queue.add(nextEvent);
                        continue;
                    } else {
                        System.out.println(event.toString());
                        if (currentCustomer.queued()) {
                            double elapsedTime = currentServer.nextAvailableTime()
                                - currentCustomer.arrivalTime();
                            totalWaitTime += elapsedTime;
                        }
                        this.queue.add(nextEvent);
                    }
                    latestShop = nextShop;
                    continue;
                }
            }
            
            //update shop to the next shop
            latestShop = nextShop;

            if (!((event instanceof ServerRest) || (event instanceof ServerBack))) {
                System.out.println(event.toString());
            }

            if (event instanceof DoneEvent) {
                customersServed++;

                if (previousServer instanceof SelfCheckout) {
                    Server currentServer = latestShop
                        .find(server -> server.identifier() == previousServer.identifier()).get();


                    SelfCheckout sc = (SelfCheckout) currentServer;

                    boolean isAvailable;
                    if (sc.queueList().size() == 0) {
                        isAvailable = true;
                    } else {
                        isAvailable = false;
                    }

                    boolean waitingCustomer = (sc.queueList().size() == sc.maxQ());
                    
                    if (isAvailable == false) {

                        SelfCheckout nextServer = new SelfCheckout(
                                currentServer.identifier(),
                                isAvailable, false,
                                currentServer.nextAvailableTime(),
                                currentServer.maxQ());

                        Customer nextCus = sc.queueGet(0);

                        /*
                        System.out.println("SIM => DE SCQ before: "
                                + sc.SCQlist());
                        System.out.println("SIM => DE ServingList before: "
                                + sc.servingList());
                        */
                        sc.queueRemove(nextCus);

                        /*
                        System.out.println("SIM => DE SCQ after: " 
                                + sc.SCQlist());
                        System.out.println("SIM => DE ServingList after: "
                                + sc.servingList());

                        System.out.println("SIM => DE nextCus" 
                                + nextCus.identifier());
                        */

                        ServeEvent hijackEvent = new ServeEvent(nextCus.hijack(),
                                (Server) nextServer);
                        latestShop = nextShop
                            .replace((Server) nextServer);
                        
                        //System.out.println("SIM => DE before events:" 
                        //        + this.queue);
                        this.queue.add((Event) hijackEvent);
                        //System.out.println("SIM => DE after addition events: " 
                        //        + this.queue);
                        continue;

                    } else {
                        SelfCheckout nextServer = new SelfCheckout(
                                currentServer.identifier(),
                                isAvailable, waitingCustomer,
                                currentServer.nextAvailableTime(),
                                currentServer.maxQ());
                        latestShop = nextShop.replace((Server)
                                nextServer);
                        continue;
                    }
                }

                //curSer is a HUMAN
                double restValue = this.restGen.get();
                if (restValue < this.restProb) {

                    //checkout latest Shop
                    //System.out.println(latestShop);

                    Server currentServer = latestShop
                        .find(server -> server.identifier() == previousServer.identifier()).get();

                    List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());

                    //add in a dummy customer to maintain queue length
                    Supplier<Double> dummySupplier = () -> 0.0;
                    Customer dummyCustomer = new Customer(0, currentServer.nextAvailableTime(),
                            dummySupplier);
                    cusList.add(dummyCustomer);
                    Server beforeEditServer = currentServer.rest();
                    Server nextServer = new Server(beforeEditServer.identifier(), false,
                            beforeEditServer.hasWaitingCustomer(),
                            beforeEditServer.nextAvailableTime(),
                            beforeEditServer.maxQ(), cusList, true);

                    //edit the shop so that the server has the dummyCustomer
                    Shop amendedShop = nextShop.replace(nextServer);
                    latestShop = amendedShop;

                    ServerRest restEvent = new ServerRest(currentCustomer,
                            nextServer, this.restPeriod);

                    this.queue.add(restEvent);
                } 
                continue;
            }

            if (event instanceof ServerRest) {
                this.queue.add(nextEvent);
                continue;
            }
            if (event instanceof LeaveEvent) {
                customersLeft++;
                continue;
            }

            if (event instanceof ServerBack) {

                //edit the shop so that the server removes the dummyCustomer
                Server currentServer = latestShop
                    .find(server -> server.identifier() == previousServer.identifier()).get();

                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                cusList = cusList.stream().filter(cus -> cus.identifier() != 0)
                    .collect(Collectors.toList());

                Server nextServer = new Server(currentServer.identifier(), true,
                        currentServer.hasWaitingCustomer(), currentServer.nextAvailableTime(),
                        currentServer.maxQ(), cusList, false);
                Shop amendedShop = nextShop.replace(nextServer);
                latestShop = amendedShop;
                continue;
            } else { //WaitEvent
                this.queue.add(nextEvent);
            }
        }
        Statistics stats = new Statistics(totalWaitTime, customersServed, customersLeft);
        System.out.println(stats.toString());
    }
}

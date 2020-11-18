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
    private final double restProb;
    private final Supplier<Double> restGen;
    private final Supplier<Double> restPeriod;
    private final double greedyProb;

    /**
     * initialised with 2 Lists passed from Main.java.
     *
     * @param customerList is the list of customers
     * @param serverList is the list of servers
     */
    public Simulation(int seedValue, int numOfServers, int selfCheckout, int maxQ, int numOfCustomers, double arrivalRate, double serviceRate, double restRate, double restProb, double greedyProb) {

        RandomGenerator rg = new RandomGenerator(seedValue, arrivalRate, serviceRate, restRate);

        //Customer's suppliers
        Supplier<Double> arrivalTime = () -> rg.genInterArrivalTime();

        //ServiceTime
        Supplier<Double> serviceTime = () -> rg.genServiceTime();

        //generate customers
        List<Customer> tempList = new ArrayList<Customer>();

        double previousValue = 0.0;
        for (int id = 1; id <= numOfCustomers; id++) {
            tempList.add(new Customer(id, previousValue, serviceTime));
            previousValue += arrivalTime.get();
        }

        //tempList.forEach(x -> System.out.println(x.arrivalTime())); ok, passed

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
                    return (Server) new SelfCheckout(id, true, false, startTime, maxQ);
                }
            })
        .collect(Collectors.toList());

        this.shop = new Shop(serverList);
        
        EventComparator eventComp = new EventComparator();
        this.queue = new PriorityQueue<Event>(eventComp);
        this.restProb = restProb;
        this.restGen = () -> rg.genRandomRest();
        this.restPeriod = () -> rg.genRestPeriod();
        this.greedyProb = greedyProb;

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

                int QlistSize;
                int ID;

                //if currentServer is a SCO
                if (currentServer instanceof SelfCheckout) {
                    SelfCheckout sc = (SelfCheckout) currentServer;
                    QlistSize = sc.SCQsize();
                    ID = currentCustomer.identifier();
                    
                    if (sc.SCScheck(currentCustomer)) {
                        continue;
                    }
                    
                    //System.out.println("SIM => SE => SCQ: " + sc.SCQlist());
                    //System.out.println("SIM => SE => SCS: " + sc.SCSlist());


                    // HAVE TO REDO DONE EVENT LOGIC HERE
                }


                //if currentserver is a normal server
                else {
                    QlistSize = currentServer.cusList().size();
                    ID = currentCustomer.identifier();
                }

                if (nextEvent == null) {
                    continue;
                }

                if ((QlistSize != 0) && (ID != currentCustomer.identifier())) {
                    continue;
                } else {

                    if (previousServer.nextAvailableTime() < currentServer.nextAvailableTime()) {

                    }
                    else {
                        System.out.println(event.toString());
                        if (currentCustomer.queued()) {
                            double elapsedTime = currentServer.nextAvailableTime() - currentCustomer.arrivalTime();
                            totalWaitTime += elapsedTime;
                        }

                    }
                    this.queue.add(nextEvent);
                    latestShop = nextShop;
                    continue;
                } 
            }




            //update shop && Print Line
            latestShop = nextShop;




            if (!((event instanceof SERVER_REST) || (event instanceof SERVER_BACK))) {
                System.out.println(event.toString());
            }

            if (event instanceof DoneEvent) {
                customersServed++;

                //if the server was a SCO
                if (previousServer instanceof SelfCheckout) {

                    //no one else in the Q
                    if (nextEvent == null) {
                        continue;
                    }
                    
                    //Need to add the logic of SERVEEVENT here
                    else {
                        this.queue.add(nextEvent);
                        latestShop = nextShop;
                        continue;
                    }
                }

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
                            beforeEditServer.hasWaitingCustomer(), beforeEditServer.nextAvailableTime(),
                            beforeEditServer.maxQ(), cusList, true);

                    //edit the shop so that the server has the dummyCustomer
                    Shop amendedShop = nextShop.replace(nextServer);
                    latestShop = amendedShop;

                    SERVER_REST restEvent = new SERVER_REST(currentCustomer, nextServer, this.restPeriod);

                    this.queue.add(restEvent);
                } 
                continue;
            }

            if (event instanceof SERVER_REST) {
                this.queue.add(nextEvent);
                continue;
            }
            if (event instanceof LeaveEvent) {
                customersLeft++;
                continue;
            }

            if (event instanceof SERVER_BACK) {

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
            }
            else { //WaitEvent
                this.queue.add(nextEvent);
            }
        }

        Statistics stats = new Statistics(totalWaitTime, customersServed, customersLeft);
        System.out.println(stats.toString());
    }
}

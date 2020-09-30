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
     * initialised with 2 Lists passed from Main.java
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
     * Main logic of simulation where the Server's isAvailable and
     * hasWaitingCustomer truth values determine which event takes
     * place next. Event that occurs next could also affect the
     * Server's truth value accordingly.
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
                customersServed++;
                DoneEvent currEvent = (DoneEvent) event;

                double time = event.getCurrentTime();
                Server currentServer = currEvent.getServer();

                for (int i = 0; i < this.serverList.size(); i++) {
                    Server s = this.serverList.get(i);
                    if (s == currentServer) {
                        Server newServer;

                        if (currentServer.getHasWaitingCustomer()) {
                            newServer = new Server(currentServer.getId(), false, false, time);

                        } else {
                            newServer = new Server(currentServer.getId(), true, false, time);
                        }


                        this.serverList.set(i, newServer);
                        break;
                    }
                }
                continue;
            }

            if (event instanceof ServeEvent) {

                ServeEvent currEvent = (ServeEvent) event;
                Server currentServer = currEvent.getServer();
                Server s;

                for (int i = 0; i < this.serverList.size(); i++) {
                    Server server = this.serverList.get(i);
                    
                    if (server == currentServer) {
                        double arrivalTime = currentCustomer.getArrivalTime();
                        double serveTime = arrivalTime + currEvent.getServiceTime();

                        s = new Server(currentServer.getId(), false, false, serveTime);
                        this.serverList.set(i, s);

                        DoneEvent nextEvent = new DoneEvent(currentCustomer, this.serverList, s);
                        this.queue.add(nextEvent);

                        break;
                    }
                }
                continue;
            }

            if (event instanceof WaitEvent) {

                WaitEvent currEvent = (WaitEvent) event;
                Server currentServer = currEvent.getServer();

                Server newServer;

                for (int i = 0; i < this.serverList.size(); i++) {
                    Server s = this.serverList.get(i);
                    if (s == currentServer) {

                        double newTime = s.getAvailableTime();
                        double elapsedTime = newTime - currentCustomer.getArrivalTime();
                        totalWaitTime += elapsedTime;

                        newServer = new Server(currentServer.getId(), false, true, newTime);
                        this.serverList.set(i, newServer);
                        Customer c = new Customer(currentCustomer.getId(),newTime);

                        ServeEvent nextEvent = new ServeEvent(c,this.serverList, newServer);
                        this.queue.add(nextEvent);
                        break;
                    }
                }
                continue;
            }

            if (event instanceof ArriveEvent) {
                Event nextEvent = event.execute();
                this.queue.add(nextEvent);
            }
        }

        double averageTime = totalWaitTime / customersServed;
        String message = String.format("[%.3f %d %d]", averageTime, customersServed, customersLeft);
        System.out.println(message);
    }
}

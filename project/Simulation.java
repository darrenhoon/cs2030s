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
                DoneEvent currEvent = (DoneEvent) event;

                double time = event.getCurrentTime();
                Server currentServer = currEvent.getServer();

                for (int i = 0; i < this.serverList.size(); i++) {
                    Server s = this.serverList.get(i);
                    if (s == currentServer) {


                        Server newServer;
                        if (currentServer.getHasWaitingCustomer()) { //ie there is a customer that was queuing up                     
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

                Server newServer;

                for (int i = 0; i < this.serverList.size(); i++) {
                    Server s = this.serverList.get(i);

                    double nextAvailableTime = currentCustomer.getArrivalTime() + 1.0;
                    if (s == currentServer) {

                        double serveTime = currentCustomer.getArrivalTime() + 1.0;

                        newServer = new Server(currentServer.getId(), false, false, serveTime);
                        this.serverList.set(i, newServer);

                       // System.out.println("Server's status: " + newServer.toString());
                        DoneEvent nextEvent = new DoneEvent(currentCustomer,this.serverList, newServer);
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

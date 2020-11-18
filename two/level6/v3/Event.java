//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents an abstract event where all events inherit from.
 */
abstract class Event {
    private final Customer customer;
    private final Server server;
    private final Function<Shop, Pair<Shop, Event>> func;
    private final int priority;
    private static Supplier<Double> serviceTime;
    private static Supplier<Double> randomRest;
    private static Supplier<Double> restPeriod;
    private static Supplier<Double> customerType;

    public Event(Customer customer, Function<Shop, Pair<Shop, Event>> func, int priorityNum) {
        this.customer = customer;
        this.func = func;
        this.server = null;
        this.priority = priorityNum;
    }

    public Event(Customer c, Function<Shop, Pair<Shop, Event>> func, Server s, int priorityNum) {
        this.customer = c;
        this.func = func;
        this.server = s;
        this.priority = priorityNum;
    }

    final Pair<Shop, Event> execute(Shop shop) {
        return this.func.apply(shop);
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Server getServer() {
        return this.server;
    }

    public int getPriority() {
        return this.priority;
    }

    public static double getServiceTime() {
        return serviceTime.get();
    }

    public static double getRandomRest() {
        return randomRest.get();
    }

    public static double getRestPeriod() {
        return restPeriod.get();
    }

    public static double getCustomerType() {
        return customerType.get();
    }

    public static void setServiceTime(Supplier<Double> stream) {
        serviceTime = stream;
    }

    public static void setRandomRest(Supplier<Double> stream) {
        randomRest = stream;
    }

    public static void setRestPeriod(Supplier<Double> stream) {
        restPeriod = stream;
    }

    public static void setCustomerType(Supplier<Double> stream) {
        customerType= stream;
    }
}

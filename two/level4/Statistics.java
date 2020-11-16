//package cs2030.simulator;

public class Statistics {
    
    private final double waitTime;
    private final int served;
    private final int left;
    private final double average;

    /**
     * Values from simulation are passed in here under SRP.
     * @param waitTime is to take in total waiting time
     * @param served is number of people served
     * @param left is the number of people who left without being served
     */
    Statistics(double waitTime, int served, int left) {
        this.waitTime = waitTime;
        this.served = served;
        this.left = left;
        this.average = this.waitTime / this.served;
    }

    @Override
    public String toString() {
        String message = String.format("[%.3f %d %d]", this.average, this.served, this.left);
        return message;
    }
}

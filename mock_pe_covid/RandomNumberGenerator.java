import java.util.Random;

/**
 * Helps generate some random numbers
 */
public class RandomNumberGenerator {
    private static final Random r = new Random();
   
    // Initialise the random with the seed
    static {
        reset();
    }

    /**
     * Reset the random generator with the seed
     */
    public static void reset() {
        r.setSeed(SimulationParameters.RANDOM_SEED);
    }

    /**
     * Obtain the next double generated by the random
     * generator
     * @return the pseurandom double value
     */
    public static double nextDouble() {
        return r.nextDouble();
    }

}


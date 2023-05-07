package exercise;

import java.util.Map;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    public static void main(String[] args) {
        System.out.println(getMinMax(new int[]{1, 2, 3, 4, 5}));
    }

    public static Map<String, Integer> getMinMax(int[] array) {
        MaxThread maxThread = new MaxThread(array);
        MinThread minThread = new MinThread(array);
        maxThread.start();
        minThread.start();
        LOGGER.info("Thread " + Thread.currentThread().getName() + " started");
        try {
            maxThread.join();
            minThread.join();
            LOGGER.info("Thread " + Thread.currentThread().getName() + " finished");
        } catch (InterruptedException e) {
            LOGGER.log(Level.FINE, e.getMessage());
            throw new RuntimeException(e);
        }
        return Map.of(
                "min", minThread.getMinNumber(),
                "max", maxThread.getMaxNumber()
        );
    }
}

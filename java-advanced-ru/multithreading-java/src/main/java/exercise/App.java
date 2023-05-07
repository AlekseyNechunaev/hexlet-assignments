package exercise;

import java.util.Map;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    public static Map<String, Integer> getMinMax(int[] array) {
        MaxThread maxThread = new MaxThread(array);
        MinThread minThread = new MinThread(array);
        maxThread.start();
        LOGGER.info("Thread " + maxThread.getName() + " started");
        minThread.start();
        LOGGER.info("Thread " + minThread.getName() + " started");
        try {
            maxThread.join();
            LOGGER.info("Thread " + maxThread.getName() + " finished");
            minThread.join();
            LOGGER.info("Thread " + minThread.getName() + " finished");
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, e.getMessage());
            throw new RuntimeException(e);
        }
        return Map.of(
                "min", minThread.getMinNumber(),
                "max", maxThread.getMaxNumber()
        );
    }
}

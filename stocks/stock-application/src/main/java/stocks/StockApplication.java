package stocks;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

/**
 * Starts the stock application.
 */
@Slf4j
public class StockApplication {
    /**
     * Main method to get stock application to run.
     * @param args arguments
     * @throws IOException exception.
     */
    public static void main(String[] args) throws IOException {
        new Thread(StockApp.getInstance()).start();
    }
}

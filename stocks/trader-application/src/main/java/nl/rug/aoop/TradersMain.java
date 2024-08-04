package nl.rug.aoop;

import java.io.IOException;

/**
 * Main function to start all the trader bots.
 */
public class TradersMain {
    /**
     * Main method.
     * @param args args.
     */
    public static void main(String[] args) {
        try {
            new TradersApplication();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
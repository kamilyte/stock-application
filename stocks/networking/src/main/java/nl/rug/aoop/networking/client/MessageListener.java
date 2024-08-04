package nl.rug.aoop.networking.client;

import java.util.Scanner;

import static java.lang.System.out;

/**
 * Handles message over network.
 */
public class MessageListener implements Runnable{
    private boolean running = false;
    private NetworkClient networkClient;
    private Scanner scanner;

    /**
     * Constructor.
     * @param networkClient creates connection with client
     */
    public MessageListener(NetworkClient networkClient) {
        this.networkClient = networkClient;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        running = true;
        out.println("Please enter a message.");
        while (running) {
            String userInput = scanner.nextLine();
            networkClient.send(userInput);
        }
    }

}

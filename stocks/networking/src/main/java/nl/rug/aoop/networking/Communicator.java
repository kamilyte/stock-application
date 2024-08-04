package nl.rug.aoop.networking;

/**
 * Passed to handleMessage to decouple networking from functionality of
 * message handling.
 */
public interface Communicator {
    /**
     * Sends message.
     * @param message json message
     */
    void send(String message);

    /**
     * so we can shut down the connection if needed.
     */
    void terminate();
}

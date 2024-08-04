package nl.rug.aoop.networking;

/**
 * Specifies behaviour of how the messages should be handled.
 */
public interface MessageHandler {
    /**
     * Allows to communicate the message over the network.
     * @param message json of message
     * @param communicator decouples the networking from message handling
     */
    void handleMessage(String message, Communicator communicator);
}

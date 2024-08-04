package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.messages.MQMessage;

/**
 * Interface for putting messages to the queue.
 */
public interface Producer {
    /**
     * Method to put message into the queue.
     * @param mqMessage message to be queued.
     */
    void put(MQMessage mqMessage);
}

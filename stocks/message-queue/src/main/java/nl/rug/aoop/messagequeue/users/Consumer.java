package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.messages.MQMessage;

/**
 * Interface for polling messages.
 */
public interface Consumer {

    /**
     * Poll message from the queue.
     * @return dequeued message.
     */
    MQMessage poll();
}

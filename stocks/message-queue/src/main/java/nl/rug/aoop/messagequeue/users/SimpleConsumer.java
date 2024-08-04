package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

/**
 * Class for polling messages implementing MQConsumer interface.
 */
public class SimpleConsumer implements Consumer {
    private MessageQueue messageQueue;

    /**
     * Constructor to have a queue to poll from.
     * @param messageQueue messageQueue
     */
    public SimpleConsumer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    /**
     * Removes message from the queue by polling.
     * @return dequeued message.
     */
    public MQMessage poll(){
        return this.messageQueue.dequeue();
    }
}

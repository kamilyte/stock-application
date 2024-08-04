package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.messages.MQMessage;

/**
 * Interface for a messageQueue.
 */
public interface MessageQueue {
    /**
     * Method for enqueueing.
     * @param mqMessage message to be enqueued.
     */
    void enqueue(MQMessage mqMessage);

    /**
     * Method for dequeueing.
     * @return dequeued message.
     */
    MQMessage dequeue();

    /**
     * Getting the size of the queue.
     * @return size as int.
     */
    int getSize();
}
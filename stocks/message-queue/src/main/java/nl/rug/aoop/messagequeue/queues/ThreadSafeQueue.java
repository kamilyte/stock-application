package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.messages.MQMessage;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Thread safe queue.
 */
public class ThreadSafeQueue implements MessageQueue {
    private PriorityBlockingQueue<MQMessage> threadSafeQueue;

    /**
     * Constructor.
     */
    public ThreadSafeQueue() {
        Comparator<MQMessage> messageSorter = Comparator.comparing(MQMessage::getTimeStampHash);
        this.threadSafeQueue = new PriorityBlockingQueue<MQMessage>(11, messageSorter);
    }

    /**
     * Adds message to queue.
     * @param mqMessage message to be enqueued.
     */
    @Override
    public void enqueue(MQMessage mqMessage) {
        if ((mqMessage.getTimestamp() == null) || (mqMessage.getHeader() == null) || (mqMessage.getValue() == null)) {
            throw new IllegalArgumentException("Message arguments are wrong");
        } else {
            threadSafeQueue.add(mqMessage);
        }
    }

    /**
     * Dequeues message from queue.
     * @return message dequeued
     */
    @Override
    public MQMessage dequeue() {
        if(!threadSafeQueue.isEmpty()) {
            return threadSafeQueue.remove();
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Getter.
     * @return size of queue
     */
    @Override
    public int getSize() {
        return threadSafeQueue.size();
    }
}

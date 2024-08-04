package nl.rug.aoop.messagequeue.queues;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.messagequeue.messages.MQMessage;

import java.util.*;

/**
 * Class for unordered queue.
 */
@Getter
@Setter
public class UnorderedQueue implements MessageQueue {
    private Queue<MQMessage> unorderedQueue;

    /**
     * Constructor for unordered queue.
     */
    public UnorderedQueue(){
        this.unorderedQueue = new LinkedList<>();
    }

    /**
     * Method for enqueueing.
     * @param mqMessage message to be enqueued.
     */
    public void enqueue(MQMessage mqMessage) {
        if ((mqMessage.getTimestamp() == null) || (mqMessage.getHeader() == null) || (mqMessage.getValue() == null)) {
            throw new IllegalArgumentException("Message arguments are wrong");
        } else {
            unorderedQueue.add(mqMessage);
        }
    }

    /**
     * Method to dequeue and return a message.
     * @return message.
     */
    @Override
    public MQMessage dequeue() {
        if (!unorderedQueue.isEmpty()){
            MQMessage mqMessage = unorderedQueue.peek();
            unorderedQueue.remove();
            return mqMessage;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int getSize() {
        return this.unorderedQueue.size();
    }
}
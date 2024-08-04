package nl.rug.aoop.messagequeue.queues;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.messagequeue.messages.MQMessage;

import java.util.*;

/**
 * Ordered queue class extending abstract messageQueue.
 */
@Getter
@Setter
public class OrderedQueue implements MessageQueue {
    private SortedMap<Integer, List<MQMessage>> orderedQueue;

    /**
     * Constructor for ordered message queue.
     */
    public OrderedQueue(){
        this.orderedQueue = new TreeMap<Integer, List<MQMessage>>();
    }

    /**
     * Enqueue() implementation.
     * @param mqMessage message to enqueue on the message queue.
     */
    public void enqueue(MQMessage mqMessage) {
        if ((mqMessage.getTimestamp() == null) || (mqMessage.getHeader() == null) || (mqMessage.getValue() == null)) {
            throw new IllegalArgumentException("Message arguments are wrong");
        } else {
            if(orderedQueue.get(mqMessage.getTimestamp().hashCode())== null) {
                List<MQMessage> ls = new ArrayList<>();
                ls.add(mqMessage);
                orderedQueue.put(mqMessage.getTimestamp().hashCode(), ls);
            } else{
                orderedQueue.get(mqMessage.getTimestamp().hashCode()).add(mqMessage);
            }
        }
    }

    /**
     * Dequeue() implementation.
     * @return queue without the first element.
     */
    public MQMessage dequeue() {
        if (orderedQueue.isEmpty()){
            throw new NoSuchElementException();
        } else if (orderedQueue.get(orderedQueue.firstKey()).size() == 1) {
            return orderedQueue.remove(orderedQueue.firstKey()).remove(0);
        } else {
            return orderedQueue.get(orderedQueue.firstKey()).remove(0);
        }
    }

    /**
     * Get queue size.
     * @return queue.size.
     */
    public int getSize() {
        return orderedQueue.size();
    }
}
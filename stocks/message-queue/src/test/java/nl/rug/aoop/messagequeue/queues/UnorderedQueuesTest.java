package nl.rug.aoop.messagequeue.queues;
import static org.junit.jupiter.api.Assertions.*;

import nl.rug.aoop.messagequeue.messages.MQMessage;

import org.junit.jupiter.api.Test;

import java.util.*;

public class UnorderedQueuesTest {
    private MessageQueue unQueue = new UnorderedQueue();
    private MQMessage mqMessage = new MQMessage("Test Message", "Message for testing");

    @Test
    void testEnqueue(){
        unQueue.enqueue(mqMessage);
        assertEquals(1, unQueue.getSize());
    }

    @Test
    void testDequeue1() {
        unQueue.enqueue(mqMessage);
        MQMessage testMQMessage = unQueue.dequeue();
        assertTrue(unQueue.getSize() == 0 && testMQMessage.getTimestamp() == mqMessage.getTimestamp());
    }
    @Test
    void testEnqueueNull() {
        MQMessage nullMQMessage = new MQMessage(null, "Hello");
        Exception e = assertThrows(IllegalArgumentException.class, () -> unQueue.enqueue(nullMQMessage));
    }

    @Test
    void testDequeueEmpty() {
        Exception e = assertThrows(NoSuchElementException.class, () -> unQueue.dequeue());
    }
}
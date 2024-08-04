package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.messages.MQMessage;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;


public class OrderedQueueTest {
    private MessageQueue ordQueue = new OrderedQueue();
    private MQMessage mqMessage = new MQMessage("Test Message", "Message for testing");
    private MQMessage MQMessage2 = new MQMessage("Test Message", "Message for testing");

    @Test
    void testEnqueue(){
        ordQueue.enqueue(mqMessage);
        assertEquals(1, ordQueue.getSize());
    }

    @Test
    void testDequeue1(){
        MessageQueue ordQueue = new OrderedQueue();
        ordQueue.enqueue(mqMessage);
        ordQueue.dequeue();
        assertEquals(0, ordQueue.getSize());
    }

    @Test
    void testEnqueueNull() {
        MQMessage nullMQMessage = new MQMessage(null, "Hello");
        Exception e = assertThrows(IllegalArgumentException.class, () -> ordQueue.enqueue(nullMQMessage));
    }

    @Test
    void testEnqueueSameTime() {
        ordQueue.enqueue(mqMessage);
        ordQueue.enqueue(MQMessage2);
        ordQueue.dequeue();
        ordQueue.dequeue();
        assertEquals(0, ordQueue.getSize());
    }

    @Test
    void testDequeueEmpty() {
        Exception e = assertThrows(NoSuchElementException.class, () -> ordQueue.dequeue());
    }

}
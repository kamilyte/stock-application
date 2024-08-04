package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.messages.MQMessage;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThreadSafeQueueTest {
    private MessageQueue threadQueue = new ThreadSafeQueue();
    private MQMessage mqMessage = new MQMessage("Test Message", "Message for testing");
    private MQMessage MQMessage2 = new MQMessage("Test Message", "Message for testing");

    @Test
    void testEnqueue(){
        threadQueue.enqueue(mqMessage);
        assertEquals(1, threadQueue.getSize());
    }

    @Test
    void testDequeue1(){
        threadQueue.enqueue(mqMessage);
        threadQueue.dequeue();
        assertEquals(0, threadQueue.getSize());
    }

    @Test
    void testEnqueueNull() {
        MQMessage nullMQMessage = new MQMessage(null, "Hello");
        Exception e = assertThrows(IllegalArgumentException.class, () -> threadQueue.enqueue(nullMQMessage));
    }

    @Test
    void testEnqueueSameTime() {
        threadQueue.enqueue(mqMessage);
        threadQueue.enqueue(MQMessage2);
        threadQueue.dequeue();
        threadQueue.dequeue();
        assertEquals(0, threadQueue.getSize());
    }

    @Test
    void testDequeueEmpty() {
        Exception e = assertThrows(NoSuchElementException.class, () -> threadQueue.dequeue());
    }

}

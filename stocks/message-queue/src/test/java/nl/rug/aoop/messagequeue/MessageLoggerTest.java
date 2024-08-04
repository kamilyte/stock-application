package nl.rug.aoop.messagequeue;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.factories.AbstractCommandFactory;
import nl.rug.aoop.messagequeue.factories.MessageQueueCommandFactory;
import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.messages.NetworkMessage;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MessageLoggerTest {

    @Test
    void handleMessageTest() {
        MessageQueue messageQueue = new ThreadSafeQueue();
        AbstractCommandFactory messageQueueFactory = new MessageQueueCommandFactory(messageQueue);
        MessageHandler messageHandler = new MessageLogger(messageQueueFactory);

        Communicator mockCommunicator = Mockito.mock(Communicator.class);
        MQMessage mqMessage = new MQMessage("TestingHandleMessage", "Handling message");
        String jsonString = mqMessage.convertToJSON();

        NetworkMessage networkMessage = new NetworkMessage(null, null).createPutMessage(mqMessage);
        String networkString = networkMessage.convertToJSON();

        messageHandler.handleMessage(networkString, mockCommunicator);
        assertEquals(1, messageQueue.getSize());
    }
}
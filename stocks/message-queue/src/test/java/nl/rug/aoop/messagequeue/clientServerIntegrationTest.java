package nl.rug.aoop.messagequeue;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.factories.AbstractCommandFactory;
import nl.rug.aoop.messagequeue.factories.MessageQueueCommandFactory;
import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.messagequeue.users.NetworkProducer;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.client.NetworkClient;
import nl.rug.aoop.networking.server.NetworkServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class clientServerIntegrationTest {
    @Test
    public void clientServerSingleConnectionTest() throws IOException {
        MessageQueue messageQueue = new ThreadSafeQueue();
        AbstractCommandFactory messageQueueFactory = new MessageQueueCommandFactory(messageQueue);
        CommandHandler commandHandler = messageQueueFactory.create();
        MessageHandler messageHandler = new MessageLogger(messageQueueFactory);
        try {
            NetworkServer server = new NetworkServer(6200, messageHandler);
            new Thread(server).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NetworkClient client = new NetworkClient(new InetSocketAddress("localhost", 6200), messageHandler);
        new Thread(client).start();
        NetworkProducer producer = new NetworkProducer(client);
        MQMessage mqMessage = new MQMessage("Integration Test", "Integration testing");
        producer.put(mqMessage);
        await().atMost(5, TimeUnit.SECONDS);
        assertEquals(messageQueue.getSize(), 1);
    }
}

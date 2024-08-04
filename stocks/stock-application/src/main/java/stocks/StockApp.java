package stocks;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.StocksManager;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.messagequeue.MessageLogger;
import nl.rug.aoop.messagequeue.factories.AbstractCommandFactory;
import nl.rug.aoop.messagequeue.factories.MessageQueueCommandFactory;
import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.messagequeue.users.Consumer;
import nl.rug.aoop.messagequeue.users.SimpleConsumer;
import nl.rug.aoop.model.StockExchange;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.server.NetworkServer;
import org.awaitility.core.ConditionTimeoutException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Stock server side - stock application.
 */
@Slf4j
public class StockApp implements Runnable {
    private static StockApp stockApp = null;
    private Consumer consumer;
    private Boolean running;
    private MessageHandler orderHandler;
    private MessageQueue messageQueue;

    /**
     * Constructor for stockApp.
     * @throws IOException exception
     */
    private StockApp() throws IOException {
        this.messageQueue = new ThreadSafeQueue();
        this.consumer = new SimpleConsumer(messageQueue);

        AbstractCommandFactory messageQueueFactory = new MessageQueueCommandFactory(messageQueue);
        MessageHandler messageHandler = new MessageLogger(messageQueueFactory);
        NetworkServer stocksServer = null;

        stocksServer = new NetworkServer(6200, messageHandler);

        new Thread(stocksServer).start();
        running = false;

        new SimpleViewFactory().createView(new StockExchange(StocksManager.getInstance()));
        AbstractCommandFactory orderCommandFactory = new OrderCommandFactory();
        orderHandler = new OrderMessageHandler(orderCommandFactory);
    }

    /**
     * Would use this to implement singleton pattern for the stock application, since we only need one function.
     * @return a stock application (server).
     * @throws IOException exception.
     */
    public static StockApp getInstance() throws IOException {
        if(stockApp == null) {
            stockApp = new StockApp();
        }
        return stockApp;
    }

    /**
     * Override run method so that the whole application could run as a thread.
     */
    @Override
    public void run() {
        running = true;
        while(running) {
            try {
                await().atMost(10, TimeUnit.SECONDS).until(() -> messageQueue.getSize() > 0);
                MQMessage message = consumer.poll();
                log.info("Checking in main after polling a message " + message.getValue());
                orderHandler.handleMessage(message.getValue(), null);
            } catch (ConditionTimeoutException e) {
                log.error("No orders were found.");
            }
        }
    }
}

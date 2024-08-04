package nl.rug.aoop;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.MessageLogger;
import nl.rug.aoop.messagequeue.factories.AbstractCommandFactory;
import nl.rug.aoop.messagequeue.factories.MessageQueueCommandFactory;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.client.NetworkClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for trader application, it stores a list of all the clients(traderBots).
 */
public class TradersApplication {
    /**
     * Constructor for trader application. It connects to the server and starts separate threads
     * with each trader.
     * @throws IOException exception.
     */
    public TradersApplication() throws IOException {
        List<TraderBot> traderBots = new ArrayList<>();
        InetSocketAddress address = new InetSocketAddress("localhost", 6200);

        MessageQueue messageQueue = new ThreadSafeQueue();
        AbstractCommandFactory messageQueueFactory = new MessageQueueCommandFactory(messageQueue);
        CommandHandler commandHandler = messageQueueFactory.create();
        MessageHandler messageHandler = new MessageLogger(messageQueueFactory);

        for (int i = 0; i < StocksManager.getInstance().getTraders().size(); i++) {
            Trader trader = StocksManager.getInstance().getTraders().get(i);
            NetworkClient networkClient = new NetworkClient(address, messageHandler);
            TraderBot traderBot = new TraderBot(trader, networkClient);
            traderBots.add(traderBot);
            new Thread(traderBot).start();
        }
    }
}

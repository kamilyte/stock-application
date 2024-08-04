package stocks;

import nl.rug.aoop.LimitOrder;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.factories.AbstractCommandFactory;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Message handler for orders.
 */
public class OrderMessageHandler implements MessageHandler {
    private CommandHandler commandHandler;

    /**
     * Constructor for message handler.
     * @param factory command factory.
     */
    public OrderMessageHandler(AbstractCommandFactory factory){
        commandHandler = factory.create();
    }

    /**
     * Method to handle messages.
     * @param message json of message
     * @param communicator decouples the networking from message handling
     */
    @Override
    public void handleMessage(String message, Communicator communicator) {
        LimitOrder limitOrder = new LimitOrder(message);
        Map<String, Object> params = new HashMap<>();
        params.put("commandName", limitOrder.getCommand());
        params.put("stockName", limitOrder.getStockName());
        params.put("priceOffered", limitOrder.getPriceOffered());
        params.put("stockAmount", limitOrder.getOrderAmount());
        params.put("traderID", limitOrder.getTraderID());
        params.put("limitOrder", message);
        commandHandler.executeCommand(limitOrder.getCommand(), params);
    }
}

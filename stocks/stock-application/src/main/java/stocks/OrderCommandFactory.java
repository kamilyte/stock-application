package stocks;

import nl.rug.aoop.StocksManager;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.factories.AbstractCommandFactory;
import stocks.ordercommands.BuyStocksCommand;
import stocks.ordercommands.SellStocksCommand;

import java.io.IOException;

/**
 * Constructs command factory.
 */
public class OrderCommandFactory implements AbstractCommandFactory {
    private StocksManager stocksManager;

    /**
     * Constructor.
     * @throws IOException exception.
     */
    public OrderCommandFactory() throws IOException {
        this.stocksManager = StocksManager.getInstance();
    }

    /**
     * Creates a commandHandler.
     * @return command handler.
     */
    public CommandHandler create(){
        CommandHandler commandHandler = new CommandHandler();
        try {
            commandHandler.registerCommand("Buy", new BuyStocksCommand());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            commandHandler.registerCommand("Sell", new SellStocksCommand());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commandHandler;
    }
}

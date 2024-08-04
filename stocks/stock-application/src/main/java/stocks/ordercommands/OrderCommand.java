package stocks.ordercommands;

import nl.rug.aoop.StocksManager;
import nl.rug.aoop.command.Command;

import java.io.IOException;

/**
 * Abstract class for order commands.
 */
public abstract class OrderCommand implements Command {
    /**
     * StocksManager.
     */
    protected final StocksManager stocksManager;

    /**
     * Initialise order command.
     * @throws IOException exception.
     */
    protected OrderCommand() throws IOException {
        this.stocksManager = StocksManager.getInstance();
    }
}

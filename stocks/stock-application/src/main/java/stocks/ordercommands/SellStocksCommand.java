package stocks.ordercommands;

import nl.rug.aoop.LimitOrder;
import java.io.IOException;
import java.util.Map;

/**
 * Child class for order command.
 */
public class SellStocksCommand extends OrderCommand {

    /**
     * Initialise order command.
     * @throws IOException exception.
     */
    public SellStocksCommand() throws IOException {
    }

    /**
     * Executes order "Sell".
     * @param params the parameters of the message.
     */
    @Override
    public void execute(Map<String,Object> params){
        String name = (String)params.get("stockName");
        int priceOffered = (int)params.get("priceOffered");
        int stockAmount = (int)params.get("stockAmount");
        String traderID = (String)params.get("traderID");
        LimitOrder limitOrder = new LimitOrder((String)params.get("limitOrder"));
        stocksManager.updateAfterSell(name, stockAmount, traderID);
    }
}

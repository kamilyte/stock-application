package stocks.ordercommands;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.LimitOrder;
import java.io.IOException;
import java.util.Map;

/**
 * Buy command.
 */
@Slf4j
public class BuyStocksCommand extends OrderCommand{

    /**
     * Constructor for buy command.
     * @throws IOException exception.
     */
    public BuyStocksCommand() throws IOException {
    }

    /**
     * Execute command.
     * @param params the parameters of the message,
     */
    @Override
    public void execute(Map<String, Object> params){
        String stockName = (String)params.get("stockName");
        int priceOffered = (int) params.get("priceOffered");
        int stockAmount = (int)params.get("stockAmount");
        String traderID = (String)params.get("traderID");
        LimitOrder limitOrder = new LimitOrder((String)params.get("limitOrder"));

        Long traderFunds = stocksManager.getTraderMap().get(traderID).getFunds();

        stocksManager.updateAfterBuy(stockName, priceOffered, stockAmount, traderID);
    }
}

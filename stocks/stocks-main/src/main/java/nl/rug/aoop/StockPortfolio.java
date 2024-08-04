package nl.rug.aoop;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing each trader's stock portfolio as a map.
 */
@Setter
@Getter
public class StockPortfolio {
    private Map<String, Integer> ownedShares;

    /**
     * Constructor for stock portfolio.
     */
    public StockPortfolio(){
        this.ownedShares = new HashMap<>();
    }

    /**
     * Adds stock after successful "buy" order.
     * @param stockName stock name which was bought.
     * @param stocksOwned number of stocks the trader has.
     */
    public void addShare(String stockName, int stocksOwned){
        ownedShares.put(stockName, stocksOwned);
    }
}
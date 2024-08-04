package nl.rug.aoop.model;

import nl.rug.aoop.Stock;
import nl.rug.aoop.StocksManager;
import nl.rug.aoop.Trader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Has information about all stocks and traders.
 * "Stock manager" of the view.
 */
public class StockExchange implements StockExchangeDataModel{
    private List<Stock> stockCollection;
    private List<Trader> traders;

    /**
     * Constructor.
     * @param stocksManager gives information about stocks and traders.
     * @throws IOException exception.
     */
    public StockExchange(StocksManager stocksManager) throws IOException {
        stockCollection = new ArrayList<>();
        for(Map.Entry<String, Stock> pair: stocksManager.getStockCollection().getStocks().entrySet()) {
            this.stockCollection.add(pair.getValue());
        }
        this.traders = stocksManager.getTraders();
    }

    /**
     * get stock using index for the list.
     * @param index The index of the stock that should be accessed.
     * @return stockDataModel.
     */
    @Override
    public StockDataModel getStockByIndex(int index) {
        Stock stock = stockCollection.get(index);
        return new StockData(stock);
    }

    /**
     * Returns number of stocks.
     * @return integer.
     */
    @Override
    public int getNumberOfStocks() {
        return stockCollection.size();
    }

    /**
     * Returns a trader model.
     * @param index The index of the trader that should be accessed.
     * @return traderDataModel.
     */
    @Override
    public TraderDataModel getTraderByIndex(int index) {
        Trader trader = traders.get(index);
        return new TraderData(trader);
    }

    /**
     * returns number of traders.
     * @return integer.
     */
    @Override
    public int getNumberOfTraders() {
        return traders.size();
    }
}

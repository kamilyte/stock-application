package nl.rug.aoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Object to generate random "buy" or "sell" orders.
 */
public class RandomOrderGenerator{
    private String order;
    private final StocksManager stocksManager;
    private final Random r;
    private final ArrayList<String> stockCollection;

    /**
     * Constructor for a order generator.
     * @throws IOException exception.
     */
    public RandomOrderGenerator() throws IOException {
        r = new Random();
        order = "";
        this.stocksManager = StocksManager.getInstance();
        stockCollection = new ArrayList<>();
        for(Map.Entry<String,Stock> entry : stocksManager.getStockCollection().getStocks().entrySet()) {
            this.stockCollection.add(entry.getKey());
        }
    }

    /**
     * Produces a single order.
     * @return order message.
     */
    public String get() {
        order = "";
        order += getBuyOrSell();
        order += ',';
        String stockName = getRandomStockName();
        order += stockName;
        order += ',';
        int numToBuy = getRandomNumber();
        order += numToBuy;
        order += ',';
        order += getOfferPrice(stockName, numToBuy);
        order += ',';
        return order;
    }

    /**
     * Returns the price similar to the actual price.
     * @param stockName name of stock.
     * @param numToBuy number of stocks to buy.
     * @return offered price..
     */
    public long getOfferPrice(String stockName, int numToBuy) {
        Stock stock = stocksManager.getStockCollection().getStocks().get(stockName);
        long price = (long) stock.getInitialPrice() * numToBuy;
        return r.nextInt((int) price - (2 * (int)stock.getInitialPrice()),
                (int) price + (2 * (int)stock.getInitialPrice()));
    }

    /**
     * Gets random number for stock amount to buy.
     * @return random number.
     */
    public int getRandomNumber() {
        return r.nextInt(5,150);
    }

    /**
     * Chooses randomly between buy and sell orders.
     * @return "buy" or "sell"
     */
    public String getBuyOrSell() {
        int num = r.nextInt(2);
        if(num == 1) {
            return "Buy";
        } else {
            return "Sell";
        }
    }

    /**
     * Get a random stock name from a stock list.
     * @return stock symbol.
     */
    public String getRandomStockName() {
        int bound = stocksManager.getStockCollection().getStocks().size();
        int index = r.nextInt(bound);
        return this.stockCollection.get(index);
    }
}

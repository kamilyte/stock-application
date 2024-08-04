package nl.rug.aoop.model;

import nl.rug.aoop.Stock;

/**
 * Data of one stock for the model.
 */
public class StockData implements StockDataModel{
    private Stock stock;

    /**
     * Constructor for one stock.
     * @param stock stock.
     */
    public StockData(Stock stock) {
        this.stock = stock;
    }

    /**
     * Getter for stock symbol.
     * @return stock symbol.
     */
    @Override
    public String getSymbol() {
        return this.stock.getSymbol();
    }

    /**
     * Getter for name.
     * @return name.
     */
    @Override
    public String getName() {
        return this.stock.getName();
    }

    /**
     * Getter for shares.
     * @return number of shares.
     */
    @Override
    public long getSharesOutstanding() {
        return this.stock.getSharesOutstanding();
    }

    /**
     * Calculate market cap.
     * @return market cap.
     */
    @Override
    public double getMarketCap() {
        return stock.getSharesOutstanding()* stock.getInitialPrice();
    }

    /**
     * Get price.
     * @return price.
     */
    @Override
    public double getPrice() {
        return this.stock.getInitialPrice();
    }
}

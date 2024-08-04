package nl.rug.aoop;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stocks manager.
 */
@Getter
@Slf4j
public class StocksManager {
    private static StocksManager stocksManager;
    private List<Trader> traders;
    private Map<String, Trader> traderMap;
    private StockCollection stockCollection;

    private final Map<String, LimitOrder> buyOrders;

    private final Map<String, LimitOrder> sellOrders;

    /**
     * Constructs an object which process all the traders and stocks.
     * @throws IOException throws.
     */
    public StocksManager() throws IOException {
        stockCollection = new YamlLoader(Path.of("data","stocks.yaml")).load(StockCollection.class);
        log.info("CheckStocks: " + getStockCollection().getStocks().size());
        traders = List.of(new YamlLoader(Path.of("data","traders.yaml")).load(Trader[].class));
        traderMap = produceMap(traders);
        log.info("CheckTraders: " + traders.size());
        this.buyOrders = new HashMap<>();
        this.sellOrders = new HashMap<>();
    }

    /**
     * If there is no instance of this object, construct a new one.
     * @return one and only stocksManager.
     * @throws IOException exception.
     */
    public static StocksManager getInstance() throws IOException {
        if(stocksManager == null) {
            stocksManager = new StocksManager();
        }
        return stocksManager;
    }

    /**
     * Helper function to produce a map of all the traders with their ID as key.
     * @param traders list of traders.
     * @return map of traders.
     */
    public Map<String, Trader> produceMap(List<Trader> traders) {
        Map<String, Trader> map = new HashMap<>();
        for(Trader trader : traders) {
            map.put(trader.getId(), trader);
        }
        return map;
    }

    /**
     * Adds buy order to the map.
     * @param order order.
     */
    public void addBuyOrder(LimitOrder order){
        buyOrders.put(order.getStockName(), order);
    }

    /**
     * Adds sell order to the map.
     * @param order order.
     */
    public void addSellOrder(LimitOrder order){
        sellOrders.put(order.getStockName(), order);
    }

    /**
     * Updates stock information and trader information after "buy" order.
     * @param stockName name of stock.
     * @param priceOffered price.
     * @param stockAmount number of stocks.
     * @param traderID trader's id.
     */
    public void updateAfterBuy(String stockName, int priceOffered, int stockAmount, String traderID) {
        Boolean traderUpdated;
        Trader trader = this.traderMap.get(traderID);
        Stock stock = stockCollection.getStocks().get(stockName);

        long actualPrice = stock.getInitialPrice() * stockAmount;

        traderUpdated = trader.updateAfterBuy(stockName, priceOffered, stockAmount, actualPrice);
        if (traderUpdated) {
            stock.updateAfterBuy(priceOffered, stockAmount);
        }
    }

    /**
     * Updates stock and trader information after "sell" order.
     * @param stockName stockName.
     * @param stockAmount stockAmount.
     * @param traderID traderID.
     */
    public void updateAfterSell(String stockName, int stockAmount, String traderID) {
        Boolean traderUpdated;
        Trader trader = this.traderMap.get(traderID);
        Stock stock = stockCollection.getStocks().get(stockName);

        long profit = stock.getInitialPrice() * stockAmount;

        traderUpdated = trader.updateAfterSell(stockName, stockAmount, profit);
        if (traderUpdated) {
            stock.updateAfterSold((int) profit, stockAmount);
        }
    }

    /*
    public void updateAfterBuy(String stockName, int priceOffered,
                               int stockAmount, String traderID, LimitOrder limitOrder) {
        Trader trader = this.traderMap.get(traderID);
        Stock stock = stockCollection.getStocks().get(stockName);
        LimitOrder minOrder = limitOrder;
        if (getSellOrders().isEmpty()) {
            addBuyOrder(limitOrder);
        } else {
            for (Map.Entry<String, LimitOrder> sellOrder : sellOrders.entrySet()) {
                String sellTraderID = sellOrder.getValue().getTraderID();
                Trader sellTrader = this.traderMap.get(sellTraderID);
                String sellStockName = sellOrder.getValue().getStockName();
                Stock sellStock = stockCollection.getStocks().get(sellStockName);
                int sellPriceOffered = sellOrder.getValue().getPriceOffered();
                int sellOrderAmount = sellOrder.getValue().getOrderAmount();
                if (!sellTrader.equals(trader) && sellStock.equals(stock) && (sellOrderAmount == stockAmount)
                        && (sellPriceOffered <= priceOffered) && (sellPriceOffered <= minOrder.getPriceOffered())) {
                    minOrder = sellOrder.getValue();
                }
            }
            if (!minOrder.equals(limitOrder)){
                this.sellOrders.remove(stockName, minOrder);
                stock.setInitialPrice(minOrder.getPriceOffered());
                int totalPrice = minOrder.getPriceOffered();
                Trader newTrader = this.traderMap.get(minOrder.getTraderID());
                if (totalPrice <= trader.getFunds()) {
                    trader.updateBuyOrder(stockName, totalPrice, stockAmount);
                    newTrader.updateSellOrder(stockName, totalPrice, stockAmount);
                    log.info("Buy completed with trader " + limitOrder.getTraderID() + " having " +
                            trader.getStockPortfolio().getOwnedShares().get(stockName));
                } else {
                    log.info("Insufficient funds");
                }
            } else {
                addBuyOrder(limitOrder);
            }
        }
    }
     */

    /*
    public void updateAfterSell(String stockName, int priceOffered, int stockAmount,
    String traderID, LimitOrder limitOrder) {
        Trader trader = this.traderMap.get(traderID);
        Stock stock = stockCollection.getStocks().get(stockName);
        LimitOrder maxOrder = limitOrder;
        if (getBuyOrders().isEmpty()){
            addSellOrder(limitOrder);
        } else {
            for (Map.Entry<String, LimitOrder> buyOrder : buyOrders.entrySet()) {
                String buyTraderID = buyOrder.getValue().getTraderID();
                Trader buyTrader = this.traderMap.get(buyTraderID);
                String buyStockName = buyOrder.getValue().getStockName();
                Stock buyStock = stockCollection.getStocks().get(buyStockName);
                int buyPriceOffered = buyOrder.getValue().getPriceOffered();
                int buyOrderAmount = buyOrder.getValue().getOrderAmount();
                if (!buyTrader.equals(trader) && buyStock.equals(stock) && (buyOrderAmount == stockAmount)
                        && (buyPriceOffered >= priceOffered) && (buyPriceOffered >= maxOrder.getPriceOffered())) {
                    maxOrder = buyOrder.getValue();
                }
            }
            if (!maxOrder.equals(limitOrder)){
                this.buyOrders.remove(stockName, maxOrder);
                stock.setInitialPrice(maxOrder.getPriceOffered());
                int totalPrice = maxOrder.getPriceOffered();
                Trader newTrader = this.traderMap.get(maxOrder.getTraderID());
                if (totalPrice <= newTrader.getFunds()) {
                    newTrader.updateBuyOrder(stockName, totalPrice, stockAmount);
                    trader.updateSellOrder(stockName, totalPrice, stockAmount);
                    log.info("Sell completed with trader " + limitOrder.getTraderID() +
                            " having " + trader.getStockPortfolio().getOwnedShares().get(stockName));
                } else {
                    log.info("Insufficient funds");
                }
            } else {
                addSellOrder(limitOrder);
            }
        }

    }
     */
}

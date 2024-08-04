package nl.rug.aoop.model;

import nl.rug.aoop.Trader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Most important traders' data to be used in model.
 */
public class TraderData implements TraderDataModel{
    private Trader trader;

    /**
     * initialise trader model.
     * @param trader trader.
     */
    public TraderData(Trader trader) {
        this.trader = trader;
    }

    /**
     * returns trader id.
     * @return trader id.
     */
    @Override
    public String getId() {
        return trader.getId();
    }

    /**
     * returns trader name.
     * @return trader name.
     */
    @Override
    public String getName() {
        return trader.getName();
    }

    /**
     * returns funds.
     * @return trader funds.
     */
    @Override
    public double getFunds() {
        return trader.getFunds();
    }

    /**
     * Get a list of stock names instead of map.
     * @return list of stock names.
     */
    @Override
    public List<String> getOwnedStocks() {
        List<String> ownedStocks = new ArrayList<>();
        for(Map.Entry<String, Integer> pair : this.trader.getStockPortfolio().getOwnedShares().entrySet()) {
            ownedStocks.add(pair.getKey());
        }
        return ownedStocks;
    }
}

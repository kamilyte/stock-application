package nl.rug.aoop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for individual trader.
 */
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Trader {
    private String name;
    private String id;
    private Long funds;
    private StockPortfolio stockPortfolio;

    /**
     * Update trader information after "Buy" function.
     * @param stockName stock name.
     * @param priceOffered price offered.
     * @param stockAmount stock amount.
     * @param actualPrice actual price.
     * @return whether the trader was updated or not.
     */
    public Boolean updateAfterBuy(String stockName, int priceOffered, int stockAmount, long actualPrice) {
        int total;
        if(priceOffered >= actualPrice && funds >= priceOffered) {
            this.setFunds(this.funds - priceOffered);

            if(this.stockPortfolio.getOwnedShares().get(stockName) == null) {
                total = stockAmount;
            } else {
                total = this.stockPortfolio.getOwnedShares().get(stockName) + stockAmount;
            }
            this.stockPortfolio.addShare(stockName, total);
            log.info("Buy completed with trader " + this.getId() + "bought " + stockAmount
                    + " having " + this.getStockPortfolio().getOwnedShares().get(stockName));
            return true;
        } else {
            log.info("Insufficient funds");
            return false;
        }
    }

    /**
     * Updates trader after "Sell" function.
     * @param stockName stock name.
     * @param stockAmount stock amount.
     * @param profit profit.
     * @return whether the trader was updated.
     */
    public Boolean updateAfterSell(String stockName, int stockAmount, long profit) {
        int total;

        if (this.stockPortfolio.getOwnedShares().get(stockName) == null) {
            total = 0;
        } else {
            total = this.stockPortfolio.getOwnedShares().get(stockName);
        }

        if(total > stockAmount) {
            this.stockPortfolio.addShare(stockName, total - stockAmount);
            this.setFunds(this.getFunds() + profit);
            log.info("Sell completed: trader " + this.getId() + " sold " + stockName + " " + stockAmount
                    + " and how has " + this.getStockPortfolio().getOwnedShares().get(stockName));
            return true;
        }
        log.info("Not enough " + stockName  + " in stocks portfolio.");
        return false;
    }
}


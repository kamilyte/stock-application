package nl.rug.aoop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to store information about individual stocks.
 */
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Stock {
    private String symbol;
    private String name;
    private long sharesOutstanding;
    private long initialPrice;

    /**
     * Adds bought shares to shares outstanding after buying and changes the initial price.
     * @param stockAmount number of bought stocks.
     * @param priceOffered price a trader offered for those stocks.
     */
    public void updateAfterBuy(int priceOffered, int stockAmount) {
        log.info("Before buy update: " + this.symbol + " " + this. initialPrice);
        if (stockAmount != 0) {
            this.initialPrice = initialPrice + (priceOffered / stockAmount);
        }
        this.sharesOutstanding = sharesOutstanding + stockAmount;
        log.info("After buy update: " + this.symbol + " " + this. initialPrice);
    }

    /**
     * Removes sold shares from shares outstanding, changes the initial price.
     * @param priceOffered price offered.
     * @param stockAmount stock amount.
     */
    public void updateAfterSold(int priceOffered, int stockAmount) {
        log.info("Before sell update: " + this.symbol + " " + this. initialPrice + " " + (priceOffered/stockAmount));
        this.initialPrice = (initialPrice-(initialPrice-priceOffered/stockAmount));
        this.sharesOutstanding = sharesOutstanding - stockAmount;
        log.info("After sell update: " + this.symbol + " " + this. initialPrice);
    }
}


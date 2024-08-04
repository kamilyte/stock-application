package stocks;


import nl.rug.aoop.Stock;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class StockTest {
    Stock stock = new Stock();

    @Test
    void updateAfterBuy() {
        stock.setInitialPrice(20);
        stock.setSharesOutstanding(10);

        int priceOffered = 100;
        int stockAmount = 5;

        stock.updateAfterBuy(priceOffered, stockAmount);
        assertEquals(stock.getSharesOutstanding(), 15);
        assertEquals(stock.getInitialPrice(), 20 + (priceOffered / stockAmount));
    }

    @Test
    void updateAfterSold() {
        stock.setInitialPrice(10);
        stock.setSharesOutstanding(30);

        int priceOffered = 100;
        int stockAmount = 5;
        stock.updateAfterSold(priceOffered, stockAmount);

        assertEquals(stock.getInitialPrice(), 10-(10-priceOffered/stockAmount));
        assertEquals(stock.getSharesOutstanding(), 25);
    }
}
package nl.rug.aoop;

import lombok.Getter;
import lombok.Setter;

/**
 * Class for individual order.
 */
@Getter
@Setter
public class LimitOrder {
    private String command;
    private String traderID;
    private String stockName;
    private int priceOffered;
    private int orderAmount;

    /**
     * Constructs a limit order from the message string.
     * @param message message which contains order information.
     */
    public LimitOrder(String message){
        String[] msg = message.split(",");
        this.command = msg[0];
        this.stockName = msg[1];
        this.orderAmount = Integer.parseInt(msg[2]);
        this.priceOffered = Integer.parseInt(msg[3]);
        this.traderID = msg[4];
    }
}

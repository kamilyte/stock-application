package nl.rug.aoop;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Class for stockCollection to be able to read it from .yaml data file.
 */
@Getter
@Setter
public class StockCollection {
    private Map<String, Stock> stocks;
}

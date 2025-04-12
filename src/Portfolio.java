import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    private double cash;
    private final Map<Stock, Integer> stocksInPortfolio;

    public Portfolio(double initialCash) {
        this.cash = initialCash;
        stocksInPortfolio = new HashMap<>();
    }

    /**
     * Buys stocks
     * @param stock    stock to buy
     * @param quantity
     * @return true if quantity of that stock can be bought, otherwise false
     */
    public boolean addStock(@NotNull Stock stock, int quantity) {
        if (quantity <= 0 ||cash < stock.getInitialPrice() * quantity) {
            return false;
        }

        if (stocksInPortfolio.containsKey(stock)) {
            stocksInPortfolio.replace(stock, stocksInPortfolio.get(stock) + quantity);
        } else {
            stocksInPortfolio.put(stock, quantity);
        }

        cash -= stock.getInitialPrice() * quantity;
        return true;
    }

    /**
     * Sells stocks
     * @param stock    stock to sell
     * @param quantity
     * @return true if quantity of that stock can be sold, otherwise false
     */
    public boolean removeStock(@NotNull Stock stock, int quantity) {
        if (quantity <= 0) {
            return false;
        }

        Integer inPortfolioQuantity = stocksInPortfolio.get(stock);

        if (inPortfolioQuantity == null || quantity > inPortfolioQuantity) {
            return false;
        }

        if (quantity == inPortfolioQuantity) {
            stocksInPortfolio.remove(stock);
        } else {
            stocksInPortfolio.replace(stock, inPortfolioQuantity - quantity);
        }

        cash += stock.getInitialPrice() * quantity;
        return true;
    }

    /**
     * Calculate value of stocks in portfolio
     */
    public double calculateStockValue() {
        double result = 0;
        for (var entry : stocksInPortfolio.entrySet()) {
            result += entry.getKey().getInitialPrice() * entry.getValue();
        }
        return result;
    }

    /**
     * @return total value of portfolio (including cash and stocks)
     */
    public double calculateTotalValue() {
        return calculateStockValue() + getCash();
    }

    public double getCash() {
        return cash;
    }

    /**
     * @return unmodifiable map of stocks in portfolio
     */
    @NotNull
    public Map<Stock, Integer> getStocksInPortfolio() {
        return Collections.unmodifiableMap(stocksInPortfolio);
    }

    /**
     * @param stock type of stock to quantify
     * @return quantity of stocks
     */
    public int getStockQuantity(@NotNull Stock stock) {
        return stocksInPortfolio.getOrDefault(stock, 0);
    }
}

import java.util.HashMap;

public class Portfolio {

    private double cash;
    private HashMap<Stock, Integer> stocks;

    public Portfolio(double cash) {
        this.cash = cash;
        stocks = new HashMap<>();
    }

    /// Buys quantity of stocks
    /// If there is not enough cash in portfolio buys maximum number of them.
    /// Returns number of bought stocks
    public int addSotck(Stock stock, int quantity) {
        if (cash < stock.getPrice() * quantity) {
            quantity = (int)(cash / stock.getPrice());
        }

        if (stocks.containsKey(stock)) {
            stocks.replace(stock, stocks.get(stock) + quantity);
        } else {
            stocks.put(stock, quantity);
        }

        cash -= stock.getPrice() * quantity;
        return quantity;
    }

    /// Sells quantity of stocks
    /// If there is not enough stocks in portfolio sells all of them.
    /// Returns number of sold stocks
    public int sellSotcks(Stock stock, int quantity) {
        if (quantity >= stocks.get(stock)) {
            quantity = stocks.get(stock);
            stocks.remove(stock);
        } else {
            stocks.replace(stock, stocks.get(stock) - quantity);
        }

        cash += stock.getPrice() * quantity;
        return quantity;
    }

    /// Calculate value of all assets in portfolio.
    public double getAssetValue() {
        double result = 0;
        for (var entry : stocks.entrySet()) {
            result += entry.getKey().getPrice() * entry.getValue();
        }
        return result;
    }

    public double getCash() {
        return cash;
    }

    /// Returns quantity of owned stocks
    public int getStockQuantity(Stock stock) {
        return stocks.getOrDefault(stock, 0);
    }
}

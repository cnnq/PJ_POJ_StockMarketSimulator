import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    private double cash;
    private final Map<String, PortfolioPosition> positions;

    public Portfolio(double initialCash) {
        if (initialCash < 0) throw new IllegalArgumentException("initialCash cannot be negative");

        this.cash = initialCash;
        positions = new HashMap<>();
    }

    /**
     * Buys assets
     * @param asset asset to be bought
     * @param quantity quantity of assets to be bought
     * @return true if bought successfully, false otherwise
     */
    public boolean addAsset(@NotNull Asset asset, int quantity) {
        if (quantity <= 0 ||cash < asset.getCurrentPrice() * quantity) {
            return false;
        }

        if (positions.containsKey(asset.getSymbol())) {
            var position = positions.get(asset.getSymbol());
            position = new PortfolioPosition(asset, position.quantity() + quantity);
            positions.replace(asset.getSymbol(), position);

        } else {
            var position = new PortfolioPosition(asset, quantity);
            positions.put(asset.getSymbol(), position);
        }

        cash -= asset.getCurrentPrice() * quantity;
        return true;
    }

    /**
     * Sells assets
     * @param asset asset to be sold
     * @param quantity quantity of that asset to be sold
     * @return true if sold successfully, false otherwise
     */
    public boolean removeAsset(@NotNull Asset asset, int quantity) {
        if (quantity <= 0) {
            return false;
        }

        PortfolioPosition position = positions.get(asset.getSymbol());

        if (position == null || quantity > position.quantity()) {
            return false;
        }

        if (quantity == position.quantity()) {
            positions.remove(asset.getSymbol());
        } else {
            position = new PortfolioPosition(asset, position.quantity() - quantity);
            positions.replace(asset.getSymbol(), position);
        }

        cash += asset.getCurrentPrice() * quantity;
        return true;
    }

    /**
     * Calculate value of assets in portfolio
     */
    public double calculateAssetsValue() {
        double result = 0;
        for (var position : positions.values()) {
            result += position.asset().getCurrentPrice() * position.quantity();
        }
        return result;
    }

    /**
     * @return total value of portfolio (including cash and stocks)
     */
    public double calculateTotalValue() {
        return calculateAssetsValue() + getCash();
    }

    public double getCash() {
        return cash;
    }

    /**
     * @return unmodifiable map of stocks in portfolio
     */
    @NotNull
    public Map<String, PortfolioPosition> getPositions() {
        return Collections.unmodifiableMap(positions);
    }

    /**
     * @param asset type of asset to quantify
     * @return quantity of asset
     */
    public int getAssetQuantity(@NotNull Asset asset) {
        PortfolioPosition position = positions.get(asset.getSymbol());
        if (position == null) return 0;

        return position.quantity();
    }
}

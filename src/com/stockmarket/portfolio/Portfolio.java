package com.stockmarket.portfolio;

import com.stockmarket.exception.*;
import com.stockmarket.market.Market;
import com.stockmarket.market.Tradeable;
import com.stockmarket.model.Asset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

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
     * @param symbol symbol of asset to be bought
     * @param quantity quantity of assets to be bought
     * @param market market from which asset should be bought
     */
    public void buy(@NotNull String symbol, @Range(from = 0, to = Integer.MAX_VALUE) int quantity, @NotNull Market market)
                    throws InsufficientFundsException, AssetNotFoundException {

        if (quantity < 0) throw new IllegalArgumentException("quantity cannot be negative");

        Asset asset = market.getAsset(symbol).orElseThrow(() -> new AssetNotFoundException("Asset of symbol: " + symbol + " not found"));
        if (!(asset instanceof Tradeable tradeable) || quantity == 0) return;

        double price = tradeable.getCurrentPrice();

        if (cash < price * quantity) throw new InsufficientFundsException(symbol, cash, price * quantity);

        cash -= price * quantity;
        addAsset(asset, quantity);
    }

    /**
     * Sells assets
     * @param symbol symbol of asset to be sold
     * @param quantity quantity of assets to be sold
     * @param market market from which asset should be sold
     */
    public void sell(@NotNull String symbol, @Range(from = 0, to = Integer.MAX_VALUE) int quantity, @NotNull Market market)
                    throws AssetNotFoundException, InsufficientAssetsException {

        if (quantity < 0) throw new IllegalArgumentException("quantity cannot be negative");

        if (positions.get(symbol) == null) throw new AssetNotFoundException("Asset of symbol: " + symbol + " not found in portfolio");
        if (positions.get(symbol).quantity() < quantity) throw new InsufficientAssetsException(symbol, quantity);

        Asset asset = market.getAsset(symbol).orElseThrow(() -> new AssetNotFoundException(symbol));
        if (!(asset instanceof Tradeable tradeable) || quantity == 0) return;

        double price = tradeable.getCurrentPrice();

        cash += price * quantity;
        removeAsset(asset, quantity);
    }


    /**
     * Adds assets
     * @param asset asset to be added
     * @param quantity quantity of assets to be added
     */
    private void addAsset(@NotNull Asset asset, @Range(from = 0, to = Integer.MAX_VALUE) int quantity) {
        if (positions.containsKey(asset.getSymbol())) {
            var position = positions.get(asset.getSymbol());
            position = new PortfolioPosition(asset, position.quantity() + quantity);
            positions.replace(asset.getSymbol(), position);

        } else {
            var position = new PortfolioPosition(asset, quantity);
            positions.put(asset.getSymbol(), position);
        }
    }

    /**
     * Removes assets
     * @param asset asset to be removed
     * @param quantity quantity of that asset to be removed
     */
    private void removeAsset(@NotNull Asset asset, @Range(from = 0, to = Integer.MAX_VALUE) int quantity) {
        PortfolioPosition position = positions.get(asset.getSymbol());

        if (quantity == position.quantity()) {
            positions.remove(asset.getSymbol());

        } else {
            position = new PortfolioPosition(asset, position.quantity() - quantity);
            positions.replace(asset.getSymbol(), position);
        }
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
     * @param symbol symbol of asset to quantify
     * @return quantity of asset
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int getAssetQuantity(@NotNull String symbol) {
        PortfolioPosition position = positions.get(symbol);
        if (position == null) return 0;

        return position.quantity();
    }
}

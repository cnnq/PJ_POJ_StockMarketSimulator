package com.stockmarket.model;

import org.jetbrains.annotations.NotNull;

public abstract class Asset {

    protected final String symbol;
    protected final String name;
    protected double currentPrice;

    public Asset(@NotNull String symbol, @NotNull String name, double initialPrice) {
        if (initialPrice < 0) throw new IllegalArgumentException("initialPrice cannot be negative");

        this.symbol = symbol;
        this.name = name;
        this.currentPrice = initialPrice;
    }

    public abstract void updatePrice();

    @NotNull
    public String getSymbol() {
        return symbol;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stock stock) {
            return symbol.equals(stock.getSymbol()) && name.equals(stock.getName()) && currentPrice == stock.getCurrentPrice();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}

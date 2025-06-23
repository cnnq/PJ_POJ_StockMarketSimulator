package com.stockmarket.model;

import org.jetbrains.annotations.NotNull;

public class Bond extends Asset implements Tradeable {

    private double interestRate;

    // Required by Jackson
    public Bond() {}

    public Bond(@NotNull String symbol, @NotNull String name, double initialPrice, double interestRate) {
        super(symbol, name, initialPrice);

        if (interestRate < 0) throw new IllegalArgumentException("interestRate cannot be negative");
        this.interestRate = interestRate;
    }

    @Override
    public void updatePrice() {
        currentPrice *= (1.0 + interestRate);
    }
}

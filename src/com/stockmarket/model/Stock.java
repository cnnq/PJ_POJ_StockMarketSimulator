package com.stockmarket.model;

import com.stockmarket.market.Tradeable;
import org.jetbrains.annotations.NotNull;

public class Stock extends Asset implements Tradeable {

    public Stock(@NotNull String symbol, @NotNull String name, double initialPrice) {
        super(symbol, name, initialPrice);
    }

    @Override
    public void updatePrice() {
        currentPrice *= (1.0 + (Math.random() - 0.5) * 0.1);
    }
}

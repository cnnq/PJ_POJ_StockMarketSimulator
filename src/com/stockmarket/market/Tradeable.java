package com.stockmarket.market;

import org.jetbrains.annotations.NotNull;

public interface Tradeable {

    @NotNull
    String getSymbol();

    double getCurrentPrice();
}

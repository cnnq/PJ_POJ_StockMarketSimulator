package com.stockmarket.model;

import org.jetbrains.annotations.NotNull;

public interface Tradeable {

    @NotNull
    String getSymbol();

    double getCurrentPrice();
}

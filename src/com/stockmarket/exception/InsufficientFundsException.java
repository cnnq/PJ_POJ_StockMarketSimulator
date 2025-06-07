package com.stockmarket.exception;

import org.jetbrains.annotations.NotNull;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(@NotNull String symbol, double ownedCash, double neededCash) {
        super("Asset of symbol: " + symbol + " cannot be bought, owned cash: " + ownedCash + ", needed cash: " + neededCash);
    }
}

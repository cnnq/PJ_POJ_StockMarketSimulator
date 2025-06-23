package com.stockmarket.exception;

import org.jetbrains.annotations.NotNull;

public class InsufficientAssetsException extends Exception {
    public InsufficientAssetsException(@NotNull String symbol, int quantity) {
        super("Cannot sell " + quantity + " of " + symbol + " asset");
    }
}

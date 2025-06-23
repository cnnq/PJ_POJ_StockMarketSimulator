package com.stockmarket.exception;

import org.jetbrains.annotations.NotNull;

public class AssetNotFoundException extends Exception {
    public AssetNotFoundException(@NotNull String symbol) {
        super("Asset of symbol: " + symbol + " not found in market");
    }
}

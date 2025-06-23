package com.stockmarket.portfolio;

import com.stockmarket.model.Asset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record PortfolioPosition(@NotNull Asset asset, @Range(from = 0, to = Integer.MAX_VALUE) int quantity) { }
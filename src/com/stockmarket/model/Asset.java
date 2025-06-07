package com.stockmarket.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jetbrains.annotations.NotNull;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Stock.class, name = "stock"),
    @JsonSubTypes.Type(value = Bond.class, name = "bond")
})
public abstract class Asset {

    protected final String symbol;
    protected final String name;
    protected double currentPrice;

    // Required by Jackson
    public Asset() { symbol = null; name = null; }

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

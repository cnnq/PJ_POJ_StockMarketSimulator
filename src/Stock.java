import org.jetbrains.annotations.NotNull;

public class Stock {

    private final String symbol;
    private final String name;
    private double initialPrice;

    public Stock(@NotNull String symbol, @NotNull String name, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.initialPrice = initialPrice;
    }

    @NotNull
    public String getSymbol() {
        return symbol;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stock stock) {
            return symbol.equals(stock.getSymbol()) && name.equals(stock.getName()) && initialPrice == stock.getInitialPrice();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}

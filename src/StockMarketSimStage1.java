import java.util.Map;

public class StockMarketSimStage1 {
    public static void main(String[] args) {


        Stock nvda = new Stock("NVDA", "NVIDIA Corporation", 108.77);
        Stock tsla = new Stock("TSLA", "Tesla, Inc.", 275.81);
        Stock intc = new Stock("INTC", "Intel Corporation", 20.35);
        Stock aapl = new Stock("AAPL", "Apple Inc.", 192.56);

        Portfolio portfolio = new Portfolio(1000);

        portfolio.addStock(nvda, 3);
        portfolio.addStock(tsla, 2);
        portfolio.addStock(intc, 10);
        portfolio.addStock(aapl, 6);

        printStockReport(portfolio);

    }

    private static void printStockReport(Portfolio portfolio) {
        Map<Stock, Integer> stocksInPortfolio = portfolio.getStocksInPortfolio();

        System.out.println("--- Stan portfelu ---");
        System.out.printf("Gotówka: %,.2f\n", portfolio.getCash());

        System.out.println("Posiadane akcje:");
        for (var entry : stocksInPortfolio.entrySet()) {
            Stock stock = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("- " + stock.getSymbol() + " (" + stock.getName() + "): " + quantity + " szt. @ " + stock.getInitialPrice() + " PLN/szt. = " + (quantity * stocksInPortfolio.get(stock)) + " PLN");
        }

        System.out.printf("Stock value: %,.2f\n", portfolio.calculateStockValue());
        System.out.printf("Total value: %,.2f\n", portfolio.calculateTotalValue());
    }
}
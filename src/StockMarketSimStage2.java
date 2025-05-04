import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StockMarketSimStage2 {
    public static void main(String[] args) {

        List<Asset> assets = new ArrayList<>();
        assets.add(new Stock("NVDA", "NVIDIA Corporation", 108.77));
        assets.add(new Stock("TSLA", "Tesla, Inc.", 275.81));
        assets.add(new Stock("INTC", "Intel Corporation", 20.35));
        assets.add(new Bond("BBBB", "Bond Inc.", 192.56, 0.02));


        Portfolio portfolio = new Portfolio(1000);

        portfolio.addAsset(assets.get(0), 1);
        portfolio.addAsset(assets.get(1), 4);
        portfolio.addAsset(assets.get(2), 0);
        portfolio.addAsset(assets.get(3), 3);

        for (int i = 0; i < 10; i++) {
            for (Asset asset : assets) {
                asset.updatePrice();
            }

            printPortfolioReport(portfolio);
            System.out.println();
        }
    }

    private static void printPortfolioReport(@NotNull Portfolio portfolio) {
        Map<String, PortfolioPosition> positions = portfolio.getPositions();

        System.out.println("--- Stan portfelu ---");
        System.out.printf("Gotówka: %,.2f\n", portfolio.getCash());

        System.out.println("Posiadane akcje:");
        for (PortfolioPosition position : positions.values()) {
            Asset asset = position.asset();
            int quantity = position.quantity();
            System.out.println("- " + asset.getSymbol() + " (" + asset.getName() + "): " + quantity + " szt. @ " + asset.getCurrentPrice() + " PLN/szt. = " + (quantity * asset.getCurrentPrice()) + " PLN");
        }

        System.out.printf("Stock value: %,.2f\n", portfolio.calculateAssetsValue());
        System.out.printf("Total value: %,.2f\n", portfolio.calculateTotalValue());
    }
}
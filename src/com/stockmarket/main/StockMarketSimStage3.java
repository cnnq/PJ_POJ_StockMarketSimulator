package com.stockmarket.main;

import com.stockmarket.exception.*;
import com.stockmarket.market.Market;
import com.stockmarket.model.*;
import com.stockmarket.portfolio.Portfolio;
import com.stockmarket.portfolio.PortfolioPosition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StockMarketSimStage3 {
    public static void main(String[] args) {

        List<Asset> assetList = new ArrayList<>();
        assetList.add(new Stock("NVDA", "NVIDIA Corporation", 108.77));
        assetList.add(new Stock("TSLA", "Tesla, Inc.", 275.81));
        assetList.add(new Stock("INTC", "Intel Corporation", 20.35));
        assetList.add(new Bond("BBBB", "Bond Inc.", 192.56, 0.02));

        Market market = new Market(assetList);

        Portfolio portfolio = new Portfolio(1000);

        try {
            portfolio.buy("NVDA", 3, market);
            portfolio.buy("TSLA", 2, market);
            portfolio.buy("INTC", 1, market);

            portfolio.sell("TSLA", 2, market);

            portfolio.buy("BBBB", 3, market);

            portfolio.sell("BBBB", 1, market);

        } catch (AssetNotFoundException | InsufficientAssetsException | InsufficientFundsException e) {
            System.out.println(e.getMessage());
            return;

        } catch (IllegalArgumentException e) {
            System.out.println("Some argument was negative or null");
            return;
        }

        for (int i = 0; i < 10; i++) {
            market.updatePrices();

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

        System.out.printf("com.stockmarket.model.Stock value: %,.2f\n", portfolio.calculateAssetsValue());
        System.out.printf("Total value: %,.2f\n", portfolio.calculateTotalValue());
    }
}
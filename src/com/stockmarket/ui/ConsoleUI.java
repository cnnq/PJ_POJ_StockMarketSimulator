package com.stockmarket.ui;

import com.stockmarket.exception.AssetNotFoundException;
import com.stockmarket.exception.InsufficientAssetsException;
import com.stockmarket.exception.InsufficientFundsException;
import com.stockmarket.market.Market;
import com.stockmarket.model.Asset;
import com.stockmarket.portfolio.PortfolioPosition;
import com.stockmarket.portfolio.PortfolioService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleUI {

    private PortfolioService portfolioService;
    private Market market;

    /**
     * Manages user interface through console
     * @param portfolioService
     * @param market
     */
    public ConsoleUI(@NotNull PortfolioService portfolioService, @NotNull Market market) {
        this.portfolioService = portfolioService;
        this.market = market;
    }

    /**
     * Start main loop
     */
    public void start() {
        boolean running = true;

        // Show menu 1st time
        displayCommands();

        while (running) {
            // Read user input
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            String[] arguments = command.split(" ");

            switch (arguments[0]) {
                case "commands":
                    displayCommands();
                    break;
                case "market":
                    displayMarket();
                    break;
                case "portfolio":
                    displayPortfolio();
                    break;
                case "buy":
                    handleBuyAction(arguments);
                    break;
                case "sell":
                    handleSellAction(arguments);
                    break;
                case "next":
                    handleNextAction();
                    break;
                case "save":
                    handleSaveAction();
                    break;
                case "exit":
                    boolean succeeded = handleSaveAction();
                    if (succeeded) running = false;
                    break;
                case "abort":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }

    private void displayCommands() {
        System.out.println("commands - wyświetla liste komend");
        System.out.println("market - wyświetla informacje o giełdzie");
        System.out.println("portfolio - wyświetla informacje o portfolio");
        System.out.println("buy <symbol> <quantity> - kupuje akcje");
        System.out.println("sell <symbol> <quantity> - sprzedaje akcje");
        System.out.println("next - nastepny krok symulacji");
        System.out.println("save - zapisuje stan portfelu");
        System.out.println("exit - zapisuje stan portfelu i wyłącza aplikacje");
        System.out.println("abort - wyłącza aplikacje bez zapisywania stanu");
        System.out.println();
    }

    private void displayMarket() {
        var assetMap = market.getAssetMap();

        System.out.println("--- Giełda ---");

        for (Asset asset : assetMap.values()) {
            System.out.printf("- %s (%s): %,.2f PLN/szt.\n", asset.getSymbol(), asset.getName(), asset.getCurrentPrice());
        }
        System.out.println("--------------\n");
    }

    private void displayPortfolio() {
        var positions = portfolioService.getPositions();

        System.out.println("--- Stan portfelu ---");
        System.out.printf("Gotówka: %,.2f\n", portfolioService.getCash());

        System.out.println("Posiadane akcje:");
        for (PortfolioPosition position : positions.values()) {
            Asset asset = position.asset();
            int quantity = position.quantity();
            System.out.printf("- %s (%s): %d szt. @ %,.2f PLN/szt. = %,.2f PLN\n", asset.getSymbol(), asset.getName(), quantity, asset.getCurrentPrice(), quantity * asset.getCurrentPrice());
        }

        System.out.printf("Wartość akcji: %,.2f\n", portfolioService.calculateAssetsValue());
        System.out.printf("Wartość portfela: %,.2f\n", portfolioService.calculateTotalValue());
        System.out.println("---------------------\n");
    }

    /**
     * Handle action of buying assets
     * @param arguments argument[0] - command, argument[1] - symbol, argument[2] - quantity
     */
    private void handleBuyAction(@NotNull String[] arguments) {
        int quantity;
        try {
            quantity = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            System.out.println("Argument <quantity> was not a number");
            return;
        }

        if (quantity < 0) {
            System.out.println("Argument <quantity> cannot be negative");
            return;
        }

        String symbol = arguments[1];
        try {
            portfolioService.buy(symbol, quantity, market);
            System.out.println("Bought " + quantity + " of " + symbol);
        } catch (AssetNotFoundException e) {
            System.out.println("Asset of symbol: " + symbol + " don't exist");
        } catch (InsufficientFundsException e) {
            System.out.println("Not enough funds to buy given quantity of assets");
        }
    }

    /**
     * Handle action of selling assets
     * @param arguments argument[0] - command, argument[1] - symbol, argument[2] - quantity
     */
    private void handleSellAction(@NotNull String[] arguments) {
        int quantity;
        try {
            quantity = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            System.out.println("Argument <quantity> was not a number");
            return;
        }

        if (quantity < 0) {
            System.out.println("Argument <quantity> cannot be negative");
            return;
        }

        String symbol = arguments[1];
        try {
            portfolioService.sell(symbol, quantity, market);
            System.out.println("Sold " + quantity + " of " + symbol);
        } catch (AssetNotFoundException e) {
            System.out.println("Asset: " + symbol + " not found in portfolio");
        } catch (InsufficientAssetsException e) {
            System.out.println("Not enough assets to sell");
        }
    }

    private void handleNextAction() {
        market.updatePrices();
        System.out.println("Prices updated\n");
    }

    /**
     * Save portfolio state
     * @return true if succeeded, false otherwise
     */
    private boolean handleSaveAction() {
        System.out.println("Saving...");
        try {
            portfolioService.save();
            System.out.println("Done");
            return true;

        } catch (IOException e) {
            System.out.println("Error while saving portfolio");
            return false;
        }
    }
}

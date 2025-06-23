package com.stockmarket.main;

import com.stockmarket.market.Market;
import com.stockmarket.persistence.CsvAssetRepository;
import com.stockmarket.persistence.FilePortfolioRepository;
import com.stockmarket.portfolio.PortfolioService;
import com.stockmarket.ui.ConsoleUI;

public class StockMarketApp {
    public static void main(String[] args) {

        var assetRepository = new CsvAssetRepository();
        var market = new Market(assetRepository);

        var portfolioRepository = new FilePortfolioRepository();
        var portfolioService = new PortfolioService(portfolioRepository);
        // portfolio loaded by service

        var ui = new ConsoleUI(portfolioService, market);

        ui.start();
    }
}
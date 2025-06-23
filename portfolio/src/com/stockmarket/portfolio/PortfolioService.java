package com.stockmarket.portfolio;

import com.stockmarket.exception.AssetNotFoundException;
import com.stockmarket.exception.InsufficientAssetsException;
import com.stockmarket.exception.InsufficientFundsException;
import com.stockmarket.market.Market;
import com.stockmarket.persistence.PortfolioRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.util.Map;

public class PortfolioService {

    public static int DEFAULT_PORTFOLIO_CASH = 1000;

    private PortfolioRepository portfolioRepository;
    private Portfolio portfolio;


    public PortfolioService(@NotNull PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolio = portfolioRepository.load().orElseGet(() -> new Portfolio(DEFAULT_PORTFOLIO_CASH));
    }

    public void buy(@NotNull String symbol, @Range(from = 0, to = Integer.MAX_VALUE) int quantity, @NotNull Market market)
            throws AssetNotFoundException, InsufficientFundsException {

        portfolio.buy(symbol, quantity, market);
    }

    public void sell(@NotNull String symbol, @Range(from = 0, to = Integer.MAX_VALUE) int quantity, @NotNull Market market)
            throws AssetNotFoundException, InsufficientAssetsException {

        portfolio.sell(symbol, quantity, market);
    }

    public double calculateAssetsValue() {
        return portfolio.calculateAssetsValue();
    }

    public double calculateTotalValue() {
        return portfolio.calculateTotalValue();
    }

    public double getCash() {
        return portfolio.getCash();
    }

    @NotNull
    public Map<String, PortfolioPosition> getPositions() {
        return portfolio.getPositions();
    }


    public void save() throws IOException {
        portfolioRepository.save(portfolio);
    }
}

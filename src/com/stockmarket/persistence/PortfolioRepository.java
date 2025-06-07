package com.stockmarket.persistence;

import com.stockmarket.portfolio.Portfolio;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public interface PortfolioRepository {

    /**
     * Saves portfolio state
     * @param portfolio portfolio to be saved
     * @throws IOException
     */
    void save(@NotNull Portfolio portfolio) throws IOException;

    /**
     * Loads portfolio state
     * @return Optional.empty() if method couldn't load file
     */
    Optional<Portfolio> load();
}

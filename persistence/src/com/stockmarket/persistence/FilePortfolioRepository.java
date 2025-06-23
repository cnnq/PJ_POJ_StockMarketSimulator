package com.stockmarket.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.portfolio.Portfolio;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class FilePortfolioRepository implements PortfolioRepository {

    public static final String DEFAULT_FILE_PATH = "src/main/resources/portfolio.json";

    @Override
    public void save(@NotNull Portfolio portfolio) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(portfolio);

        FileWriter writer = new FileWriter(DEFAULT_FILE_PATH);
        writer.write(json);
        writer.close();
    }

    @Override
    public Optional<Portfolio> load() {

        File file = new File(DEFAULT_FILE_PATH);
        if(!file.exists()) return Optional.empty();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Portfolio portfolio = objectMapper.readValue(file, Portfolio.class);
            return Optional.of(portfolio);

        } catch (IOException e) {
            // Couldn't deserialize file
            return Optional.empty();
        }
    }
}

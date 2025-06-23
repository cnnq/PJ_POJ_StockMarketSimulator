package com.stockmarket.persistence;

import com.stockmarket.exception.InvalidCsvException;
import com.stockmarket.model.Asset;
import com.stockmarket.model.Bond;
import com.stockmarket.model.Stock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvAssetRepository implements AssetRepository {

    @Override
    public Map<String, Asset> loadAssetDefinitions() throws IOException, InvalidCsvException {

        Map<String, Asset> assets = new HashMap<>();

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(CsvConfig.DEFAULT_ASSETS_PATH));
        } catch (IOException e) {
            throw new IOException(e);
        }

        for (int i = 1; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");

            if (fields.length < 4) throw new InvalidCsvException(lines.get(i));

            String symbol = fields[0];
            String name = fields[1];
            String type = fields[2];
            if (symbol.isBlank() || name.isBlank() || type.isBlank()) throw new InvalidCsvException(lines.get(i));

            double price;
            try {
                price = Double.parseDouble(fields[3]);
            } catch (NumberFormatException e) {
                throw new InvalidCsvException(lines.get(i));
            }

            if (price < 0) throw new InvalidCsvException(lines.get(i));

            Asset asset;

            if (type.equals("STOCK")) {
                asset = new Stock(symbol, name, price);
            } else if (type.equals("BOND")) {
                asset = new Bond(symbol, name, price, 0.01f);
            } else {
                throw new InvalidCsvException(lines.get(i));
            }

            assets.put(symbol, asset);
        }

        return assets;
    }
}

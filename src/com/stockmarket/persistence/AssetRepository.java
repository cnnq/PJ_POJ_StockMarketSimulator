package com.stockmarket.persistence;

import com.stockmarket.exception.InvalidCsvException;
import com.stockmarket.model.Asset;

import java.io.IOException;
import java.util.Map;

public interface AssetRepository {

    /**
     * Load asset definitions from a file
     * @return String-asset map when loaded successfully.
     * @throws IOException when file couldn't be loaded
     * @throws InvalidCsvException when csv file was somehow corrupted
     */
    Map<String, Asset> loadAssetDefinitions() throws IOException, InvalidCsvException;
}

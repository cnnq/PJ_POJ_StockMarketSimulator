package com.stockmarket.market;

import com.stockmarket.exception.InvalidCsvException;
import com.stockmarket.model.Asset;
import com.stockmarket.persistence.AssetRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class Market {

    private Map<String, Asset> assetMap;


    public Market(@NotNull AssetRepository assetRepository) {
        try {
            this.assetMap = assetRepository.loadAssetDefinitions();
        } catch (IOException | InvalidCsvException e) {
            System.out.println("Exception occurred while loading asset definitions: " + e.getMessage());
            System.out.println("Creating empty asset map");
            this.assetMap = new HashMap<>();
        }
    }

    public Optional<Asset> getAsset(@NotNull String symbol) {
        return Optional.ofNullable(assetMap.get(symbol));
    }

    public void updatePrices() {
        for (Asset asset : assetMap.values()) {
            asset.updatePrice();
        }
    }

    @NotNull
    public Map<String, Asset> getAssetMap() {
        return Collections.unmodifiableMap(assetMap);
    }
}

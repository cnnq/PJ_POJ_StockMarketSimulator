package com.stockmarket.market;

import com.stockmarket.model.Asset;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Market {

    private Map<String, Asset> assetMap;

    public Market(@NotNull List<Asset> assetList) {
        assetMap = new HashMap<>();
        for (Asset asset : assetList) {
            assetMap.put(asset.getSymbol(), asset);
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

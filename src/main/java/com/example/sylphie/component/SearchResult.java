package com.example.sylphie.component;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private final List<Long> itemIds;
    private final List<Long> minPrices;

    public SearchResult() {
        this.itemIds = new ArrayList<>();
        this.minPrices = new ArrayList<>();
    }

    public void addResult(long itemId, long minPrice) {
        this.itemIds.add(itemId);
        this.minPrices.add(minPrice);
    }

}

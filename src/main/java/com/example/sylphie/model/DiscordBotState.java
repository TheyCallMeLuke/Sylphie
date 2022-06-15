package com.example.sylphie.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
public class DiscordBotState {

    private Map<Long, Long> itemIdToMaxPrice;

    private List<VendingItem> goodVendingItems;

    private boolean isRunning;

    private int currentPage;

    public DiscordBotState() {
        this.itemIdToMaxPrice = new HashMap<>();
        this.goodVendingItems = new ArrayList<>();
    }

}

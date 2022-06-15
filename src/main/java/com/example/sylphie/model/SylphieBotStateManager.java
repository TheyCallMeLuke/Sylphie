package com.example.sylphie.model;

import com.example.sylphie.util.SylphieMessageFormatter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SylphieBotStateManager {

    private final DiscordBotState botState;

    public SylphieBotStateManager(DiscordBotState botState) {this.botState = botState;}

    public void start() {
        botState.setRunning(true);
        botState.setCurrentPage(0);
    }

    public boolean isNotRunning() {
        return !this.isRunning();
    }

    public boolean isCheapVendingItem(VendingItem vendingItem) {
        return vendingItem.getPrice() <= botState.getItemIdToMaxPrice().getOrDefault(
                vendingItem.getId(),
                Long.MIN_VALUE
        );
    }

    public boolean isRunning() {
        return botState.isRunning();
    }

    public void nextPage() {
        botState.setCurrentPage(botState.getCurrentPage() + 1);
    }

    public int getCurrentPage() {
        return botState.getCurrentPage();
    }

    public MessageEmbed getStatus() {
        return SylphieMessageFormatter.formatStatusMessage(botState);
    }

    public void setGoodVendingItems(List<VendingItem> vendingItems) {
        botState.setGoodVendingItems(vendingItems);
    }

    public void addToWatchList(long itemId, long maxPrice) {
        botState.getItemIdToMaxPrice().put(itemId, maxPrice);
    }

    public void removeFromWatchList(long itemId) {
        botState.getItemIdToMaxPrice().remove(itemId);
    }

    public boolean isItemNotInWatchList(long itemId) {
        return !isItemInWatchList(itemId);
    }

    private boolean isItemInWatchList(long itemId) {
        return botState.getItemIdToMaxPrice().containsKey(itemId);
    }

    public MessageEmbed getGoodVendingItemsStatus() {
        return SylphieMessageFormatter.formatGoodVendingItemsMessage(botState);
    }

    public boolean isWatchListEmpty() {
        return botState.getItemIdToMaxPrice().isEmpty();
    }

    public void stopWatching() {
        botState.setRunning(false);
        botState.setItemIdToMaxPrice(new HashMap<>());
        botState.setGoodVendingItems(new ArrayList<>());
        botState.setCurrentPage(0);
    }
}

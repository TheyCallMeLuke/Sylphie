package com.example.sylphie.util;

import com.example.sylphie.model.DiscordBotState;
import com.example.sylphie.model.VendingItem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class SylphieMessageFormatter {

    public static MessageEmbed formatStatusMessage(DiscordBotState botState) {
        String message = formatWatchList(botState.getItemIdToMaxPrice());
        return new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("This is my current status", null)
                .addField("Is the watcher running?", botState.isRunning() ? "Yes" : "No", false)
                .addField(
                        "Which page am I scanning right now?",
                        botState.getCurrentPage() > 0 ? String.valueOf(botState.getCurrentPage()) : "Not scanning right now",
                        false
                )
                .addField("Watch list", message.isEmpty() ? "Empty" : message, false)
                .addField(
                        "Good vending items",
                        SylphieMessageFormatter.formatVendingItems(botState.getGoodVendingItems()),
                        false
                )
                .build();

    }

    public static MessageEmbed formatGoodVendingItemsMessage(DiscordBotState botState) {
        return new EmbedBuilder()
                .setColor(Color.GREEN)
                .addField(
                        "Found items",
                        SylphieMessageFormatter.formatVendingItems(botState.getGoodVendingItems()),
                        false
                )
                .build();
    }

    private static String formatWatchList(Map<Long, Long> itemIdToMaxPrice) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Long, Long> entry : itemIdToMaxPrice.entrySet()) {
            String message = String.format("id %s, max price %d", entry.getKey(), entry.getValue());
            stringBuilder
                    .append(message)
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    private static String formatVendingItems(List<VendingItem> vendingItems) {
        StringBuilder stringBuilder = new StringBuilder();
        for (VendingItem vendingItem : vendingItems) {
            String message = String.format(
                    "id %s, price %d, amount %d",
                    vendingItem.getItem().getId(),
                    vendingItem.getPrice(),
                    vendingItem.getAmount()
            );
            stringBuilder
                    .append(message)
                    .append("\n");
        }
        return stringBuilder.toString().isEmpty() ? "Empty" : stringBuilder.toString();
    }
}

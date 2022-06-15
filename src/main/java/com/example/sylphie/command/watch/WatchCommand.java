package com.example.sylphie.command.watch;

import com.example.sylphie.command.defaults.CommandExecutor;
import com.example.sylphie.command.defaults.CommandInfo;
import com.example.sylphie.command.defaults.CommandSender;
import com.example.sylphie.model.SylphieBotStateManager;
import com.example.sylphie.model.VendingItem;
import com.example.sylphie.network.DocumentFetcher;
import com.example.sylphie.parser.VendingShopParser;
import com.example.sylphie.parser.VendorListParser;
import com.example.sylphie.parser.result.VendorListParsingResult;
import com.example.sylphie.parser.result.VendorShopParsingResult;
import com.example.sylphie.parser.util.Constants;
import com.example.sylphie.component.DiscordMessage;
import com.example.sylphie.util.ItemParseUtil;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Qualifier("watch")
public class WatchCommand implements CommandExecutor {

    public static Logger LOGGER = Logger.getLogger(WatchCommand.class.getName());

    private final SylphieBotStateManager stateManager;
    private final DocumentFetcher documentFetcher;
    private final VendingShopParser vendingShopParser;
    private final VendorListParser vendorListParser;

    public WatchCommand(
            SylphieBotStateManager botStateManager,
            DocumentFetcher documentFetcher, VendingShopParser vendingShopParser,
            VendorListParser vendorListParser
    ) {
        this.stateManager = botStateManager;
        this.documentFetcher = documentFetcher;
        this.vendingShopParser = vendingShopParser;
        this.vendorListParser = vendorListParser;
    }

    @CommandInfo(value = "watch", minArguments = 2, maxArguments = 2, usage = "<item_id> <max_price>")
    @Override
    public void execute(DiscordMessage discordMessage, CommandSender commandSender) {
        List<String> args = discordMessage.getArgs();
        long itemId;
        long maxPrice;
        try {
            itemId = ItemParseUtil.parseItemId(args.get(0), commandSender);
            maxPrice = ItemParseUtil.parseMaxPrice(args.get(1), commandSender);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        stateManager.addToWatchList(itemId, maxPrice);
        LOGGER.info(String.format("Added item with id %d with max price %d to the watch list", itemId, maxPrice));
        commandSender.sendMessage(String.format(
                "Added item with id %d with max price %d to the watch list",
                itemId,
                maxPrice
        ));

        if (stateManager.isNotRunning()) {
            stateManager.start();
            LOGGER.info("Starting watch mode");
            Thread thread = new Thread(() -> {
                startWatching(commandSender);
                LOGGER.info("Stopped watch mode");
                commandSender.sendMessage("Stopped");
            });
            thread.start();
        }
    }

    private void startWatching(CommandSender commandSender) {
        while (stateManager.isRunning()) {
            LOGGER.fine("Entire market scanned. Restart.");
            scan(commandSender);
            if (stateManager.isNotRunning()) {
                break;
            }
        }
    }

    private void scan(CommandSender commandSender) {
        List<VendingItem> goodVendingItems = new ArrayList<>();
        while (true) {
            stateManager.nextPage();
            LOGGER.fine("Scanning page: " + stateManager.getCurrentPage());
            String vendorListUrl = MessageFormat.format(
                    "{0}&{1}={2}",
                    Constants.VENDING_URL,
                    Constants.PAGE_PARAMETER,
                    stateManager.getCurrentPage()
            );
            try {
                Document vendorListDocument = documentFetcher.fetch(vendorListUrl);
                VendorListParsingResult vendorListParsingResult = vendorListParser.parse(vendorListDocument);
                List<String> shopUrls = vendorListParsingResult.getShopUrls();
                for (String shopUrl : shopUrls) {
                    LOGGER.fine("Scanning shop URL " + shopUrl);
                    Document shopDocument = documentFetcher.fetch(shopUrl);
                    VendorShopParsingResult vendorShopParsingResult = vendingShopParser.parse(shopDocument);
                    List<VendingItem> vendingItems = vendorShopParsingResult.getVendingItems();
                    for (VendingItem vendingItem : vendingItems) {
                        if (stateManager.isNotRunning()) {
                            return;
                        }
                        if (stateManager.isCheapVendingItem(vendingItem)) {
                            goodVendingItems.add(vendingItem);
                        }
                    }
                }
                if (vendorListParsingResult.isLastResult()) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stateManager.setGoodVendingItems(goodVendingItems);
        LOGGER.fine(String.format(
                "Added items %s into the watch list",
                stateManager.getGoodVendingItemsStatus().toString()
        ));
        MessageEmbed messageEmbed = stateManager.getGoodVendingItemsStatus();
        commandSender.sendEmbedMessage(messageEmbed);
    }

}

package com.example.sylphie.command.unwatch;

import com.example.sylphie.command.defaults.CommandExecutor;
import com.example.sylphie.command.defaults.CommandInfo;
import com.example.sylphie.command.defaults.CommandSender;
import com.example.sylphie.component.DiscordMessage;
import com.example.sylphie.component.SylphieBotStateManager;
import com.example.sylphie.util.ItemParseUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@Qualifier("unwatch")
public class UnwatchCommand implements CommandExecutor {

    public static Logger LOGGER = Logger.getLogger(UnwatchCommand.class.getName());

    private final SylphieBotStateManager stateManager;

    public UnwatchCommand(SylphieBotStateManager botStateManager) {
        this.stateManager = botStateManager;
    }

    @CommandInfo(value = "unwatch", minArguments = 1, maxArguments = 1, usage = "<item_id>")
    @Override
    public void execute(DiscordMessage discordMessage, CommandSender commandSender) {
        List<String> args = discordMessage.getArgs();
        long itemId;
        try {
            itemId = ItemParseUtil.parseItemId(args.get(0), commandSender);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        if (stateManager.isItemNotInWatchList(itemId)) {
            commandSender.sendMessage(String.format("Item %d is not in the watch list", itemId));
            return;
        }
        stateManager.removeFromWatchList(itemId);
        LOGGER.info(String.format("Removed item %d from the watch list", itemId));
        commandSender.sendMessage(String.format("Removed %d from the watch list", itemId));
        if (stateManager.isWatchListEmpty()) {
            stateManager.stopWatching();
        }
    }

}

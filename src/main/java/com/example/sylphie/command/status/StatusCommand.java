package com.example.sylphie.command.status;

import com.example.sylphie.command.defaults.CommandExecutor;
import com.example.sylphie.command.defaults.CommandInfo;
import com.example.sylphie.command.defaults.CommandSender;
import com.example.sylphie.model.BotStateManager;
import com.example.sylphie.component.DiscordMessage;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("status")
public class StatusCommand implements CommandExecutor {

    private final BotStateManager stateManager;

    public StatusCommand(BotStateManager botStateManager) {
        this.stateManager = botStateManager;
    }

    @CommandInfo(value = "status")
    @Override
    public void execute(DiscordMessage discordMessage, CommandSender commandSender) {
        MessageEmbed messageEmbed = stateManager.getStatus();
        commandSender.sendEmbedMessage(messageEmbed);
    }

}

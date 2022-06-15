package com.example.sylphie.command.defaults;

import com.example.sylphie.component.DiscordMessage;

public interface CommandExecutor {

    void execute(DiscordMessage discordMessage, CommandSender commandSender);

}

package com.example.sylphie.command.defaults;

import net.dv8tion.jda.api.entities.MessageEmbed;

public interface CommandSender {

    void sendEmbedMessage(MessageEmbed message);

    void sendMessage(String message);

}

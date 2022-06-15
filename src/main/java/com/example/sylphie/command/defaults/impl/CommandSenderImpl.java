package com.example.sylphie.command.defaults.impl;

import com.example.sylphie.command.defaults.CommandSender;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class CommandSenderImpl implements CommandSender {

    private final MessageChannel messageChannel;

    public CommandSenderImpl(MessageChannel messageChannel) {this.messageChannel = messageChannel;}

    @Override
    public void sendEmbedMessage(MessageEmbed message) {
        this.messageChannel.sendMessageEmbeds(message).queue();
    }

    @Override
    public void sendMessage(String message) {
        this.messageChannel.sendMessage(message).queue();
    }
}

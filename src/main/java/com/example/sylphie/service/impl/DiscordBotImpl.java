package com.example.sylphie.service.impl;

import com.example.sylphie.command.defaults.Command;
import com.example.sylphie.command.defaults.CommandSender;
import com.example.sylphie.command.defaults.impl.CommandSenderImpl;
import com.example.sylphie.component.DiscordMessage;
import com.example.sylphie.configuration.AppProperties;
import com.example.sylphie.service.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DiscordBotImpl implements DiscordBot {

    private final AppProperties properties;

    private JDA jda;

    public DiscordBotImpl(AppProperties properties) {
        this.properties = properties;
    }

    @Override
    public void start() throws LoginException {
        jda = JDABuilder.createLight(properties.getToken())
                .setEventManager(new AnnotatedEventManager())
//                .setActivity(Activity.playing(properties.getPrefix()))
                .build();
    }

    @Override
    public void stop() {
        jda.shutdown();
    }

    @Override
    public JDA getJda() {
        return jda;
    }

    @Override
    public void registerCommand(Command command) {
        jda.addEventListener(new AnnotatedEventManager() {
            @SubscribeEvent
            public void messageReceivedEvent(MessageReceivedEvent event) {
                processMessage(event, command);
            }
        });
    }

    private void processMessage(MessageReceivedEvent event, Command command) {
        String messageContent = event.getMessage().getContentRaw();

        if (messageContent.startsWith(properties.getPrefix() + command.getName())) {
            DiscordMessage discordMessage = new DiscordMessage(event, command);
            this.processCommand(discordMessage);
        }
    }

    private void processCommand(DiscordMessage discordMessage) {
        MessageChannel messageChannel = discordMessage.getEvent().getChannel();
        CommandSender commandSender = new CommandSenderImpl(messageChannel);
        Command command = discordMessage.getCommand();
        String messageContent = discordMessage.getEvent().getMessage().getContentRaw();

        List<String> args = Arrays.stream(messageContent.trim().replaceAll(" +", " ").split(" "))
                .skip(1)
                .collect(Collectors.toList());

        String usage = properties.getPrefix() + command.getName() + command.getUsage();

        if ((args.size() < command.getMinArguments()) || (args.size() > command.getMaxArguments())) {
            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setFooter("Invalid pattern " + usage, null)
                    .build();
            messageChannel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        discordMessage.getCommand().getExecutor().execute(discordMessage, commandSender);
    }

}
















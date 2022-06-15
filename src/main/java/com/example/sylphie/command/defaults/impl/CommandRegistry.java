package com.example.sylphie.command.defaults.impl;

import com.example.sylphie.command.defaults.Command;
import com.example.sylphie.command.defaults.CommandExecutor;
import com.example.sylphie.command.defaults.CommandInfo;
import com.example.sylphie.service.DiscordBot;

import java.lang.reflect.Method;

public class CommandRegistry {

    private final DiscordBot discordBot;

    public CommandRegistry(DiscordBot discordBot) {this.discordBot = discordBot;}

    public void registerByExecutors(CommandExecutor... commandExecutors) {
        for (CommandExecutor commandExecutor : commandExecutors) {
            Method[] methods = commandExecutor.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandInfo.class)) {
                    CommandInfo commandInfo = method.getAnnotation(CommandInfo.class);
                    Command command = new CommandImpl.CommandImplBuilder()
                            .name(commandInfo.value())
                            .usage(commandInfo.usage())
                            .minArguments(commandInfo.minArguments())
                            .maxArguments(commandInfo.maxArguments())
                            .executor(commandExecutor)
                            .build();

                    this.discordBot.registerCommand(command);
                }
            }
        }
    }
}

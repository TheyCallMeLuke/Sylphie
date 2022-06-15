package com.example.sylphie.command.defaults.impl;

import com.example.sylphie.command.defaults.Command;
import com.example.sylphie.command.defaults.CommandExecutor;
import lombok.Builder;

@Builder
public class CommandImpl implements Command {

    private String name;
    private String usage;
    private int minArguments;
    private int maxArguments;
    private CommandExecutor executor;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public void setMinArguments(int minArguments) {
        this.minArguments = minArguments;
    }

    @Override
    public void setMaxArguments(int maxArguments) {
        this.maxArguments = maxArguments;
    }

    @Override
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public int getMinArguments() {
        return minArguments;
    }

    @Override
    public int getMaxArguments() {
        return maxArguments;
    }

    @Override
    public CommandExecutor getExecutor() {
        return executor;
    }
}

package com.example.sylphie.service;

import com.example.sylphie.command.defaults.Command;
import net.dv8tion.jda.api.JDA;

import javax.security.auth.login.LoginException;

public interface DiscordBot {

    void start() throws LoginException;

    void stop();

    JDA getJda();

    void registerCommand(Command command);

}

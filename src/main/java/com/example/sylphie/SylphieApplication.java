package com.example.sylphie;

import com.example.sylphie.command.defaults.CommandExecutor;
import com.example.sylphie.command.defaults.impl.CommandRegistry;
import com.example.sylphie.service.DiscordBot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

@SpringBootApplication
public class SylphieApplication implements CommandLineRunner {

    private static final Logger LOGGER = Logger.getLogger(SylphieApplication.class.getName());

    private final CommandRegistry commandRegistry;
    private final DiscordBot discordBot;

    private final CommandExecutor watchCommandExecutor;
    private final CommandExecutor unwatchCommandExecutor;
    private final CommandExecutor statusCommandExecutor;

    public SylphieApplication(
            CommandRegistry commandRegistry,
            DiscordBot discordBot,
            @Qualifier("watch") CommandExecutor watchCommand,
            @Qualifier("unwatch") CommandExecutor unwatchCommandExecutor,
            @Qualifier("status") CommandExecutor statusCommandExecutor
    ) {
        this.commandRegistry = commandRegistry;
        this.discordBot = discordBot;
        this.watchCommandExecutor = watchCommand;
        this.unwatchCommandExecutor = unwatchCommandExecutor;
        this.statusCommandExecutor = statusCommandExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(SylphieApplication.class, args);
    }

    @Override
    public void run(String... args) throws LoginException {
        discordBot.start();
        LOGGER.info("Discord bot started");
        this.commandRegistry.registerByExecutors(
                watchCommandExecutor,
                unwatchCommandExecutor,
                statusCommandExecutor
        );
    }
}

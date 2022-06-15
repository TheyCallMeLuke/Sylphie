package com.example.sylphie.configuration;

import com.example.sylphie.command.defaults.impl.CommandRegistry;
import com.example.sylphie.service.DiscordBot;
import com.example.sylphie.service.impl.DiscordBotImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringApplicationConfiguration {

    private final AppProperties properties;

    public SpringApplicationConfiguration(AppProperties properties) {this.properties = properties;}

    @Bean
    public DiscordBot discordBotBean() {
        return new DiscordBotImpl(this.properties);
    }

    @Bean
    public CommandRegistry commandRegistryBean() {
        return new CommandRegistry(this.discordBotBean());
    }
}

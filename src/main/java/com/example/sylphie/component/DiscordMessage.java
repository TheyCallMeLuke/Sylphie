package com.example.sylphie.component;

import com.example.sylphie.command.defaults.Command;
import lombok.Getter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DiscordMessage {

    private final MessageReceivedEvent event;
    private final Command command;

    private String rawMessage;
    private List<String> args;

    public DiscordMessage(MessageReceivedEvent event, Command command) {
        this.event = event;
        this.command = command;
        this.parseMessage();
    }

    private void parseMessage() {
        this.rawMessage = event.getMessage().getContentRaw();
        this.args = Arrays.stream(rawMessage.split(" "))
                .skip(1)
                .collect(Collectors.toList());
    }
}

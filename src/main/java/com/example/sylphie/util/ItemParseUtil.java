package com.example.sylphie.util;

import com.example.sylphie.command.defaults.CommandSender;

public class ItemParseUtil {

    public static long parseMaxPrice(String s, CommandSender commandSender) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(String.format("The max price must be a number, but %s was found instead", s));
            throw e;
        }
    }

    public static long parseItemId(String s, CommandSender commandSender) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(String.format("The item id must be a number, but %s was found instead", s));
            throw e;
        }
    }

}

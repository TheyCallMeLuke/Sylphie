package com.example.sylphie.util;

import com.example.sylphie.command.defaults.CommandSender;
import com.example.sylphie.util.impl.CommandSenderMockImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemParseUtilTest {

    private final CommandSender commandSender = new CommandSenderMockImpl();

    @Test
    void correctMaxPriceParsingShouldReturnParsedNumber() {
        String inputString = "1234";
        long maxPrice = ItemParseUtil.parseMaxPrice(inputString, commandSender);
        assertThat(maxPrice).isEqualTo(Long.parseLong(inputString));
    }

    @Test
    void incorrectMaxPriceParsingShouldThrowException() {
        String inputString = "not a number";
        assertThrows(
                NumberFormatException.class,
                () -> ItemParseUtil.parseMaxPrice(inputString, commandSender),
                "Expected parseMaxPrice(String, CommandSender) to throw, but it didn't"
        );
    }

    @Test
    void correctItemIdParsingShouldReturnParsedNumber() {
        String inputString = "1234";
        long itemId = ItemParseUtil.parseItemId(inputString, commandSender);
        assertThat(itemId).isEqualTo(Long.parseLong(inputString));
    }

    @Test
    void incorrectItemIdParsingShouldThrowException() {
        String inputString = "not a number";
        assertThrows(
                NumberFormatException.class,
                () -> ItemParseUtil.parseItemId(inputString, commandSender),
                "Expected parseItemId(String, CommandSender) to throw, but it didn't"
        );
    }
}
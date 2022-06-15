package com.example.sylphie.parser.util;

import java.text.MessageFormat;

public class Constants {

    public static final String BASE_URL = "https://www.shining-moon.com";

    public static final String MODULE_PARAMETER = "module";

    public static final String VENDING_MODULE_NAME = "vending";

    public static final String PAGE_PARAMETER = "p";

    public static final String VENDING_URL = MessageFormat.format(
            "{0}/?{1}={2}",
            BASE_URL,
            MODULE_PARAMETER,
            VENDING_MODULE_NAME
    );

    public static final int MAX_NUMBER_OF_SHOPS_PER_PAGE = 20;
}

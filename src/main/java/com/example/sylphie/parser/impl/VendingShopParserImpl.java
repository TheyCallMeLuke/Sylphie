package com.example.sylphie.parser.impl;

import com.example.sylphie.component.Item;
import com.example.sylphie.component.VendingItem;
import com.example.sylphie.parser.VendingShopParser;
import com.example.sylphie.parser.result.VendorShopParsingResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendingShopParserImpl implements VendingShopParser {

    @Override
    public VendorShopParsingResult parse(Document document) {
        Elements elements = document.select(".horizontal-table tbody tr");
        List<VendingItem> vendingItems;
        try {
            vendingItems = elements.stream()
                    .map(element -> {
                        Elements itemElements = element.select("td");
                        if (itemElements.size() < 10) {
                            throw new IllegalArgumentException("Error: shop table has less than 10 columns at " + document.baseUri());
                        }

                        Element aId = itemElements.get(0).selectFirst("a");
                        Element aName = itemElements.get(1).selectFirst("a");
                        Element ninthColumnElement = itemElements.get(9);
                        Element eighthColumnElement = itemElements.get(8);

                        if (aId == null) {
                            throw new IllegalArgumentException("Error: could not find item id at " + document.baseUri());
                        }
                        if (aName == null) {
                            throw new IllegalArgumentException("Error: could not find item name at " + document.baseUri());
                        }

                        int itemId = Integer.parseInt(aId.text());
                        String itemName = aName.text();
                        int price = Integer.parseInt(zennyToInt(eighthColumnElement.text()));
                        int amount = Integer.parseInt(ninthColumnElement.text());

                        Item item = new Item(itemId, itemName);
                        return new VendingItem(item, price, amount);
                    })
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: NumberFormatException thrown at " + document.baseUri(), e);
        }

        return new VendorShopParsingResult(vendingItems);
    }

    private String zennyToInt(String s) {
        s = removeWhiteSpace(s);
        s = removeZ(s);
        return s;
    }

    private String removeZ(String result) {
        return result.replace("z", "");
    }

    private String removeWhiteSpace(String zenny) {
        return zenny.replace(" ", "");
    }

}

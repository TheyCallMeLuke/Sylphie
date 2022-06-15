package com.example.sylphie.parser.impl;

import com.example.sylphie.parser.VendorListParser;
import com.example.sylphie.parser.result.VendorListParsingResult;
import com.example.sylphie.parser.util.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendorListParserImpl implements VendorListParser {

    @Override
    public VendorListParsingResult parse(Document document) {
        Elements elements = document.select(".horizontal-table tbody tr");
        List<String> shopUrls = elements.stream()
                .map(element -> {
                    Elements td = element.select("td");
                    if (td.size() < 3) {
                        throw new IllegalArgumentException("Error: vendor table has less than 3 columns at " + document.baseUri());
                    }
                    Element a = td.get(2).selectFirst("a");
                    if (a == null) {
                        throw new IllegalArgumentException("Error: could not find the shop url at " + document.baseUri());
                    }
                    String relativeShopUrl = a.attr("href");
                    if (relativeShopUrl.isEmpty()) {
                        throw new IllegalArgumentException("Error: could not find the shop url at " + document.baseUri());
                    }
                    return MessageFormat.format("{0}/{1}", Constants.BASE_URL, relativeShopUrl);
                })
                .collect(Collectors.toList());
        return new VendorListParsingResult(shopUrls);
    }

}

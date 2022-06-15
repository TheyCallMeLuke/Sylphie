package com.example.sylphie.parser;

import com.example.sylphie.parser.result.VendorShopParsingResult;
import org.jsoup.nodes.Document;

import java.io.IOException;

public interface VendingShopParser {
    VendorShopParsingResult parse(Document document) throws IOException;
}

package com.example.sylphie.parser;

import com.example.sylphie.parser.result.VendorListParsingResult;
import org.jsoup.nodes.Document;

public interface VendorListParser {
    VendorListParsingResult parse(Document document);
}

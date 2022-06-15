package com.example.sylphie.network;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DocumentFetcher {

    private final JsoupConnector jsoupConnector;

    public DocumentFetcher(JsoupConnector jsoupConnector) {this.jsoupConnector = jsoupConnector;}

    public Document fetch(String url) throws IOException {
        Connection connection = jsoupConnector.connect(url);
        connection.timeout(60000);
        Document document;
        try {
            document = connection.get();
        } catch (IOException e) {
            throw new IOException("Error: could not fetch data from: " + url, e);
        }
        return document;
    }

}
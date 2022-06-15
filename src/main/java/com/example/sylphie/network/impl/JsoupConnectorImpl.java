package com.example.sylphie.network.impl;

import com.example.sylphie.network.JsoupConnector;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class JsoupConnectorImpl implements JsoupConnector {
    @Override
    public Connection connect(String url) {
        return Jsoup.connect(url);
    }
}

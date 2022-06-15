package com.example.sylphie.network;

import org.jsoup.Connection;

public interface JsoupConnector {
    Connection connect(String url);
}

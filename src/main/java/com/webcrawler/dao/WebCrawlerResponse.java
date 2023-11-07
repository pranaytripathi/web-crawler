package com.webcrawler.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebCrawlerResponse {
    public Map<String, List<String>> getWebLinksCrawled() {
        return webLinksCrawled;
    }

    public void setWebLinksCrawled(final String url, final List<String> links) {
        webLinksCrawled.putIfAbsent(url, links);
    }

    private final Map<String, List<String>> webLinksCrawled = new ConcurrentHashMap<>();
}

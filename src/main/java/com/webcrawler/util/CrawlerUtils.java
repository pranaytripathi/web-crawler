package com.webcrawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class CrawlerUtils {
    public List<String> getLinksOnUrl(final String url) throws IOException {
        Document d = Jsoup.connect(url).get();
        return d.select("a").stream().map(x -> x.attributes().get("href")).filter(x -> x.startsWith("https://")).toList();
    }

    public String getDomainOfUrl(final String url) throws URISyntaxException {
        URI uri = new URI(url);
        if (Objects.isNull(uri.getHost())) {
            throw new URISyntaxException(url, "Invalid Url");
        }
        return String.format("https://%s", uri.getHost());
    }
}

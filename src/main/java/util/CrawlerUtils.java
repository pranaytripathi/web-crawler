package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class CrawlerUtils {
    public List<String> getLinksOnUrl(final String url) throws IOException {
        Document d = Jsoup.connect(url).get();
        return d.select("a").stream().map(x -> x.attributes().get("href")).filter(x -> x.startsWith("https://")).toList();
    }

    public String getDomainOfUrl(final String url) throws URISyntaxException {
        URI uri = new URI(url);
        return String.format("https://%s", uri.getHost());
    }
}

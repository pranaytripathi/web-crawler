import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        String url  = "https://monzo.com";

        List<String> links = getLinksForUrl(url);

        links.forEach(x -> {
            if (x.startsWith(url)) {
                try {
                    Document d1 = Jsoup.connect(x).get();
                    System.out.println(d1.select("a").stream()
                            .map(l -> l.attributes().get("href"))
                            .filter(l -> l.startsWith("https://"))
                            .toList());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static List<String> getLinksForUrl(final String url) throws IOException {
        Document d = Jsoup.connect(url).get();
        return d.select("a").stream().map(x -> x.attributes().get("href")).filter(x -> x.startsWith("https://")).toList();
    }
    private static ExecutorService getExecutors() {
        return Executors.newFixedThreadPool(10);
    }

    private CompletableFuture<?> crawlLinks(final List<String> links, String url) {
        return null;
    }
}

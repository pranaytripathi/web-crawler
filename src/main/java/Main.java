import helper.CompletableFutureHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {
    private static List<String> visitedLinks = new ArrayList<>();

    private static Map<String, List<String>> output = new HashMap<>();
    public static void main(String[] args) throws IOException, URISyntaxException {
        String url  = "https://monzo.com";
        URI uri = new URI(url);
        processUrl(url, "https://"+ uri.getHost() );
        output.keySet().stream().forEach(x -> {
          System.out.println(x +"        "+ output.get(x));
        });
    }

    private static void processUrl(String url, String domain) throws IOException {
      var links = getLinksForUrl(url);
      output.putIfAbsent(url, links);
      var linksOnSameDomain = links.stream().filter(x-> x.startsWith(domain) && !output.containsKey(x)).collect(Collectors.toList());
      if (linksOnSameDomain.size() > 0) {
         var futureToCrawlLinks = crawlLinks(linksOnSameDomain, domain);
         CompletableFutureHelper.executeAllFutures(futureToCrawlLinks);
      }
    }

    private static List<String> getLinksForUrl(final String url) throws IOException {
        Document d = Jsoup.connect(url).get();
        return d.select("a").stream().map(x -> x.attributes().get("href")).filter(x -> x.startsWith("https://")).toList();
    }
    private static ExecutorService getExecutors() {
        return Executors.newFixedThreadPool(10);
    }

    private static List<CompletableFuture<Void>> crawlLinks(List<String> links, String domain)  {
       ExecutorService executors = getExecutors();
       return links.stream().map(x ->
               CompletableFuture.runAsync(() -> {
                 try {
                   processUrl(x, domain);
                 } catch (IOException e) {
                   System.out.println(e.getMessage());
                 }
               }, executors))
               .collect(Collectors.toList());
    }
}

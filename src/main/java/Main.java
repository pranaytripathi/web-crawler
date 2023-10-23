import services.WebCrawlerService;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String url = "https://www.google.com";
        WebCrawlerService service = new WebCrawlerService();
        var output = service.generateWebCrawlerResult(url).getWebLinksCrawled();
        output.keySet().forEach(x -> {
            System.out.println(x + "               " +output.get(x));
        });
    }
}

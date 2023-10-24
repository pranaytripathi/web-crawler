package com.webcrawler.services;

import com.webcrawler.dao.WebCrawlerResponse;
import com.webcrawler.util.CompletableFutureHelper;
import com.webcrawler.util.CrawlerUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebCrawlerService {
    private final WebCrawlerResponse webCrawlerResponse;

    private final ExecutorService executorService;

    private final CrawlerUtils crawlerUtils;

    public WebCrawlerService() {
        this.webCrawlerResponse = new WebCrawlerResponse();
        this.executorService = ExecutorProvider.getInstance().getExecutors();
        this.crawlerUtils = new CrawlerUtils();
    }

    public WebCrawlerResponse generateWebCrawlerResult(final String url) throws URISyntaxException, IOException {
        this.processUrl(url, crawlerUtils.getDomainOfUrl(url) );
        return webCrawlerResponse;
    }

    private void processUrl(String url, String domain) throws IOException {
        var links = crawlerUtils.getLinksOnUrl(url);
        webCrawlerResponse.setWebLinksCrawled(url, links);

        var linksOnSameDomain = links.stream().filter(
                x-> x.startsWith(domain) && !webCrawlerResponse.getWebLinksCrawled().containsKey(x))
                .collect(Collectors.toList());

        if (!linksOnSameDomain.isEmpty()) {
            var futureToCrawlLinks = crawlLinks(linksOnSameDomain, domain);
            CompletableFutureHelper.executeAllFutures(futureToCrawlLinks);
        }
    }

    private List<CompletableFuture<Void>> crawlLinks(List<String> links, String domain)  {
        return links.stream().map(x ->
                        CompletableFuture.runAsync(() -> {
                            try {
                                this.processUrl(x, domain);
                            } catch (IOException e) {
                                System.out.println(x + " " + e.getMessage());
                            }
                        }, this.executorService).orTimeout(30, TimeUnit.SECONDS))
                .collect(Collectors.toList());
    }
}

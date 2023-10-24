package com.webcrawler;

import com.webcrawler.services.WebCrawlerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the url to crawl:");
        String url = sc.next();
        WebCrawlerService service = new WebCrawlerService();
        var output = service.generateWebCrawlerResult(url).getWebLinksCrawled();
        output.keySet().forEach(x -> {
            System.out.println(x + "               " +output.get(x));
        });
    }
}

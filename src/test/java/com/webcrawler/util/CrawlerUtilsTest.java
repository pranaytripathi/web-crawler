package com.webcrawler.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URISyntaxException;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerUtilsTest {

    private CrawlerUtils crawlerUtils;

    @Before
    public void setup() {
        crawlerUtils = new CrawlerUtils();
    }

    /**
     * Test to make sure the domain is extracted from URL with path.
     * @throws URISyntaxException
     */

    @Test
    public void testURlWithPathParseSuccess() throws URISyntaxException {
        var actual = crawlerUtils.getDomainOfUrl("https://google.com/test/new");
        Assertions.assertEquals(actual, "https://google.com");
    }

    /**
     * Test to check the url is parsed successfully.
     * @throws URISyntaxException
     */

    @Test
    public void testURlParseSuccess() throws URISyntaxException {
        var actual = crawlerUtils.getDomainOfUrl("https://google.com");
        Assertions.assertEquals(actual, "https://google.com");
    }

    /**
     * Test to check the url with invalid character throws error.
     * @throws URISyntaxException
     */
    @Test(expected = URISyntaxException.class)
    public void testURlParseErrorInvalidCharactorInUrl() throws URISyntaxException {
        var actual = crawlerUtils.getDomainOfUrl("https:// google.com");
    }

    /**
     * Test to check the invalid url throws error.
     * @throws URISyntaxException
     */
    @Test(expected = URISyntaxException.class)
    public void testURlParseErrorInvalidUrl() throws URISyntaxException {
        var actual = crawlerUtils.getDomainOfUrl("abcd");
    }

}

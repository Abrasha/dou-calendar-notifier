package com.github.abrasha.dounotifier.service;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * @author Andrii Abramov on 6/8/17.
 */
@Service
public class DocumentFetcherImpl implements DocumentFetcher {

    private static final Logger log = LoggerFactory.getLogger(DocumentFetcherImpl.class.getName());

    @Override
    @SneakyThrows
    public Document fetchDocument(URL url) {

        log.debug("Fetching url: {}", url.toExternalForm());

        return Jsoup.parse(url, 10_000);
    }
}

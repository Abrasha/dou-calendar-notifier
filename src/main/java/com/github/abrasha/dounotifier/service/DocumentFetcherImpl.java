package com.github.abrasha.dounotifier.service;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

/**
 * @author Andrii Abramov on 6/8/17.
 */
@Service
public class DocumentFetcherImpl implements DocumentFetcher {
    
    @Override
    @SneakyThrows
    public Document fetchDocument(String url) {
        return Jsoup.connect(url).get();
    }
}

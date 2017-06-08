package com.github.abrasha.dounotifier.service;

import org.jsoup.nodes.Document;

import java.net.URL;

/**
 * @author Andrii Abramov on 6/8/17.
 */
public interface DocumentFetcher {
    
    Document fetchDocument(URL url);
    
}

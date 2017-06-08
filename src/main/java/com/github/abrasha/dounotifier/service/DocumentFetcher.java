package com.github.abrasha.dounotifier.service;

import org.jsoup.nodes.Document;

/**
 * @author Andrii Abramov on 6/8/17.
 */
public interface DocumentFetcher {
    
    Document fetchDocument(String url);
    
}

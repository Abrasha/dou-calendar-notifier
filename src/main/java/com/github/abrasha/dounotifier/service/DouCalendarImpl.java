package com.github.abrasha.dounotifier.service;

import com.github.abrasha.dounotifier.core.Event;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author Andrii Abramov on 6/8/17.
 */
@Service
public class DouCalendarImpl implements DouCalendar {
    
    private static final String NEWS_BLOCK = "col50 m-cola";
    private static final String NEWS_ENTRY = ".b-postcard";
    
    private final DocumentFetcher documentFetcher;
    
    @Value("${dou.calendar.root}")
    private String douCalendarRoot;
    
    @Autowired
    public DouCalendarImpl(DocumentFetcher documentFetcher) {
        this.documentFetcher = documentFetcher;
    }
    
    @Override
    public Set<Event> getEvents(String city, int page) {
        
        
        return parseEventsFromPage(city, page);
    }
    
    @Override
    public Set<Event> getAllEvents(String city) {
        
        Set<Event> result = new HashSet<>();
        
        try {
            for (int i = 0; i < 20; i++) {
                result.addAll(parseEventsFromPage(city, i));
            }
        } catch (Exception e) {
            System.out.println("Done reading news");
        }
        
        return result;
    }
    
    
    private Set<Event> parseEventsFromPage(String city, int page) {
        
        
        Document document = documentFetcher.fetchDocument(generatePageUrl(city, page));
        
        Elements select = document.getElementsByClass(NEWS_BLOCK);
        
        Set<Event> result = new HashSet<>(20);
        
        
        for (Element element : select.select(NEWS_ENTRY)) {
            System.out.println(element.html());
            
            Event event = new Event();
            
            Element title = element.select("h2>a").get(0);
            event.setTitle(title.text());
            
            Element whenAndWhere = element.select(".when-and-where").get(0);
            event.setWhenAndWhere(whenAndWhere.text());

//            Element when = element.select(".when-and-where>span").get(0);
//            event.setDate(when.text());
//
//            String where = element.select(".when-and-where").get(0).text();
//            event.setWhere(where);
//
//            Element price = element.select(".when-and-where>span").get(1);
//            event.setPrice(price.text());
            
            Element content =   element.select(".b-typo").get(0);
            event.setContent(content.text());
            
            event.setTags(new HashSet<>());
            
            for (Element tag : element.select(".more>a")) {
                event.getTags().add(tag.text());
            }
            
            
            result.add(event);
            
        }
        return result;
    }
    
    private String generatePageUrl(String city, int page) {
        
        if (page == 0) {
            return format("%s/city/%s/", douCalendarRoot, city);
        } else {
            return format("%s/city/%s/%d", douCalendarRoot, city, page);
        }
        
    }
}


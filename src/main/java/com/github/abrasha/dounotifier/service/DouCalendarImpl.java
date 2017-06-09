package com.github.abrasha.dounotifier.service;

import com.github.abrasha.dounotifier.core.Event;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author Andrii Abramov on 6/8/17.
 */
@Service
public class DouCalendarImpl implements DouCalendar {

    private static final Logger log = LoggerFactory.getLogger(DouCalendarImpl.class.getName());

    private static final String NEWS_BLOCK = "col50";
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

        Set<Event> result = new LinkedHashSet<>();

        try {
            log.debug("Parsing dou page: {}", 0);
            result.addAll(parseEventsFromPage(city, 0));
            for (int i = 2; i < 20; i++) {
                log.debug("Parsing dou page: {}", i);
                result.addAll(parseEventsFromPage(city, i));
            }
        } catch (Exception e) {
            log.debug("Done reading news with exception: {}", e);
        }

        return result;
    }


    @SneakyThrows
    private Set<Event> parseEventsFromPage(String city, int page) {


        String url = generatePageUrl(city, page);

        log.debug("Parsing url: {}", url);

        Document document = documentFetcher.fetchDocument(new URL(url));

        Element news = document.getElementsByClass(NEWS_BLOCK).first();

        Set<Event> result = new HashSet<>(20);


        for (Element element : news.select(NEWS_ENTRY)) {

            Event event = new Event();

            Element title = element.select("h2>a").get(0);
            event.setTitle(title.text());

            Element whenAndWhere = element.select(".when-and-where").get(0);
            event.setWhenAndWhere(whenAndWhere.text());

            Element content = element.select(".b-typo").get(0);
            event.setContent(content.text());

            event.setTags(new HashSet<>());

            for (Element tag : element.select(".more>a")) {
                event.getTags().add(tag.text());
            }

            log.debug("Parsed event: {}", event);
            result.add(event);

        }
        return result;
    }

    @SneakyThrows
    private String generatePageUrl(String city, int page) {

        log.debug("Encoding url: {}", city);
        city = URLEncoder.encode(city, "UTF-8");
        log.debug("Encoded url: {}", city);

        if (page == 0) {
            return format("%s/city/%s/", douCalendarRoot, city);
        } else {
            return format("%s/city/%s/%d/", douCalendarRoot, city, page);
        }

    }
}


package com.github.abrasha.dounotifier.web;

import com.github.abrasha.dounotifier.core.Event;
import com.github.abrasha.dounotifier.service.DouCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Andrii Abramov on 6/8/17.
 */
@RestController
public class DouCalendarController {

    private static final Logger log = LoggerFactory.getLogger(DouCalendarController.class.getName());

    private final DouCalendar douCalendar;

    @Autowired
    public DouCalendarController(DouCalendar douCalendar) {
        this.douCalendar = douCalendar;
    }

    @GetMapping("/events/{city}")
    public Set<Event> getEvents(@PathVariable("city") String city, @RequestParam(value = "page", defaultValue = "0") int page) {
        return douCalendar.getEvents(city, page);
    }

    @GetMapping("/events/{city}/all")
    public Set<Event> getAllEvents(@PathVariable("city") String city) {
        return douCalendar.getAllEvents(city);
    }

}

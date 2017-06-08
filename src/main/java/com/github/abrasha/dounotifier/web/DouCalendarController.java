package com.github.abrasha.dounotifier.web;

import com.github.abrasha.dounotifier.core.Event;
import com.github.abrasha.dounotifier.service.DouCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Andrii Abramov on 6/8/17.
 */
@RestController
public class DouCalendarController {
    
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

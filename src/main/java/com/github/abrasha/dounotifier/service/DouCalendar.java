package com.github.abrasha.dounotifier.service;

import com.github.abrasha.dounotifier.core.Event;

import java.util.Set;

/**
 * @author Andrii Abramov on 6/8/17.
 */
public interface DouCalendar {
    
    Set<Event> getEvents(String city, int page);
    
    Set<Event> getAllEvents(String city);
    
}

package com.github.abrasha.dounotifier.service;

import com.github.abrasha.dounotifier.core.Event;

import java.util.List;
import java.util.Set;

/**
 * @author Andrii Abramov on 6/9/17.
 */
public interface DouCalendarNotifier {

    void refreshNews();

    void sendNotifications(Set<Event> events, List<String> recipients);

}

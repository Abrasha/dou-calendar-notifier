package com.github.abrasha.dounotifier.service;

import com.github.abrasha.dounotifier.core.Event;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author Andrii Abramov on 6/9/17.
 */
@Service
public class DouCalendarNotifierImpl implements DouCalendarNotifier {

    private static final Logger log = LoggerFactory.getLogger(DouCalendarNotifierImpl.class.getName());

    private final JavaMailSender javaMailSender;
    private final DouCalendar douCalendar;

    private final Set<Event> knownEvents = new LinkedHashSet<>();

    @Value("${dou.calendar.recipients}")
    private String[] recipients;

    @Autowired
    public DouCalendarNotifierImpl(JavaMailSender javaMailSender, DouCalendar douCalendar) {
        this.javaMailSender = javaMailSender;
        this.douCalendar = douCalendar;
    }


    @Override
    @Scheduled(initialDelay = 0, fixedRate = 300_000) // 5 min
    public void refreshNews() {
        log.debug("refreshing news...");

        Set<Event> allEvents = douCalendar.getAllEvents("Киев");
        log.debug("got events: " + allEvents.size());

        if (allEvents.equals(knownEvents)) {
            System.out.println("No new news");
            return;
        }

        allEvents.removeIf(knownEvents::contains);

        knownEvents.addAll(allEvents);

        System.out.println("News to notify: " + allEvents);

        sendNotifications(allEvents, Arrays.asList(recipients));

    }

    @Override
    @SneakyThrows
    public void sendNotifications(Set<Event> events, List<String> recipients) {
        System.out.println("Sending notification to " + recipients);
        System.out.println("Content: " + events);

        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("<h1>There were new events found!</h1><br>")
                .append("<table border=\"1\">")
                .append("<thead>")
                .append("<tr>")
                .append("<td>")
                .append("What")
                .append("</td>")
                .append("<td>")
                .append("Where and When")
                .append("</td>")
                .append("<td>")
                .append("Description")
                .append("</td>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        for (Event event : events) {
            messageBuilder
                    .append("<tr>")
                    .append("<td>")
                    .append(event.getTitle())
                    .append("</td>")
                    .append("<td>")
                    .append(event.getWhenAndWhere())
                    .append("</td>")
                    .append("<td>")
                    .append(event.getContent())
                    .append("</td>")
                    .append("</tr>");
        }

        messageBuilder.append("</tbody>")
                .append("</table><br>");

        messageBuilder.append("<h2>Thank you for your attention</h2>");
        messageBuilder.append("<h3>Developed by Andrii Abramov (abramov.andrii@gmail.com)</h3>");

        Address[] objects = recipients.stream()
                .map(this::getInternetAddress)
                .toArray(Address[]::new);

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setContent(messageBuilder.toString(), "text/html; charset=UTF-8");
        message.setSubject("New events found!");
        message.setFrom("dou-notifier@gmail.com");
        message.setRecipients(Message.RecipientType.TO, objects);


        javaMailSender.send(message);

    }

    @SneakyThrows
    private InternetAddress getInternetAddress(String email) {
        return new InternetAddress(email);
    }
}

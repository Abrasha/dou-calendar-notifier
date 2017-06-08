package com.github.abrasha.dounotifier;

import com.github.abrasha.dounotifier.service.DouCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DouNotifierApplication implements CommandLineRunner {
    
    public static void main(String[] args) {
        SpringApplication.run(DouNotifierApplication.class, args);
    }
    
    @Autowired
    private DouCalendar douCalendar;
    
    @Override
    public void run(String... args) throws Exception {
        douCalendar.getAllEvents("Киев");
//        MimeMessage message = mailSender.createMimeMessage();
//        message.setText("I am body");
//        message.setSubject("Subject");
//        message.setFrom("lalka@example.com");
//        message.setRecipients(Message.RecipientType.TO, "abramov.andrii@gmail.com");
//
//        mailSender.send(message);
    }
}

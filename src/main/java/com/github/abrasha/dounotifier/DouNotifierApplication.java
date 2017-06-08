package com.github.abrasha.dounotifier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DouNotifierApplication implements CommandLineRunner {
    
    public static void main(String[] args) {
        SpringApplication.run(DouNotifierApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {

//        MimeMessage message = mailSender.createMimeMessage();
//        message.setText("I am body");
//        message.setSubject("Subject");
//        message.setFrom("lalka@example.com");
//        message.setRecipients(Message.RecipientType.TO, "abramov.andrii@gmail.com");
//
//        mailSender.send(message);
    }
}

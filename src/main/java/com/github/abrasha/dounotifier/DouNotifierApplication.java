package com.github.abrasha.dounotifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DouNotifierApplication {

    private static final Logger log = LoggerFactory.getLogger(DouNotifierApplication.class.getName());

    public static void main(String[] args) {
        log.debug("Starting DouNotifierApplication");
        SpringApplication.run(DouNotifierApplication.class, args);
        log.debug("Started DouNotifierApplication");
    }
}

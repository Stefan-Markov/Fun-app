package com.fullstackproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class FullstackProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstackProjectApplication.class, args);
    }
}

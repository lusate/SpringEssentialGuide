package com.example.springessentialguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringEssentialGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEssentialGuideApplication.class, args);
    }


}

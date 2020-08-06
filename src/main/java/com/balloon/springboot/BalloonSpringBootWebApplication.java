package com.balloon.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
public class BalloonSpringBootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalloonSpringBootWebApplication.class, args);
    }

}

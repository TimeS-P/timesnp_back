package com.charly.timesnp_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.charly.timesnp_back", "models", "org.springframework.security"})
//@EnableJpaRepositories(basePackages = {"com.charly.timesnp_back", "models"})
//@EntityScan(basePackages = {"com.charly.timesnp_back", "com/charly/timesnp_back/models"})
@EnableWebSecurity
public class TimesnpBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimesnpBackApplication.class, args);
    }

}
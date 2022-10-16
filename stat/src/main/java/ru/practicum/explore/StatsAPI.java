package ru.practicum.explore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;


@SpringBootApplication
public class StatsAPI {
    private static final String port = "9090";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StatsAPI.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));
        app.run(args);
    }
}
package com.lostway.eventnotificator;

import org.springframework.boot.SpringApplication;

public class TestEventNotificatorApplication {

    public static void main(String[] args) {
        SpringApplication.from(EventNotificatorApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

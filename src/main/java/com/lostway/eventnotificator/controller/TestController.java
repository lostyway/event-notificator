package com.lostway.eventnotificator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/not")
@Slf4j
public class TestController {

    @GetMapping
    public String getInfo() {
        log.info("info");
        return "Hello World from event notificator";
    }
}

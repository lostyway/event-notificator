package com.lostway.eventnotificator.utility;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@UtilityClass
public class ClockUtil {
    private final Clock clock = Clock.system(ZoneId.of("Europe/Moscow"));

    public LocalDateTime getMoscowTimeNow() {
        return LocalDateTime.now(clock);
    }
}

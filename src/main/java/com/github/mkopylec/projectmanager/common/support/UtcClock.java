package com.github.mkopylec.projectmanager.common.support;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Component
public class UtcClock extends Clock {

    private final Clock value = systemUTC();

    @Override
    public ZoneId getZone() {
        return value.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return value.withZone(zone);
    }

    @Override
    public Instant instant() {
        return value.instant();
    }
}

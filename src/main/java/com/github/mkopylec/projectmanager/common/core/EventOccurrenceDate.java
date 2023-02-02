package com.github.mkopylec.projectmanager.common.core;

import java.time.Clock;
import java.time.Instant;

public class EventOccurrenceDate extends Value<Instant> {

    EventOccurrenceDate(Clock clock) {
        super(clock.instant());
    }
}

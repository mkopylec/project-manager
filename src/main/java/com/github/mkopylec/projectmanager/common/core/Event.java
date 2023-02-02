package com.github.mkopylec.projectmanager.common.core;

import java.time.Clock;

public abstract class Event {

    private final EventId eventId;
    private final EventOccurrenceDate occurrenceDate;

    protected Event(Clock clock) {
        this.eventId = new EventId();
        this.occurrenceDate = new EventOccurrenceDate(clock);
    }

    public EventId getEventId() {
        return eventId;
    }

    public EventOccurrenceDate getOccurrenceDate() {
        return occurrenceDate;
    }
}

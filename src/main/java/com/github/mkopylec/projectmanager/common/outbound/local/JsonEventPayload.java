package com.github.mkopylec.projectmanager.common.outbound.local;

import com.github.mkopylec.projectmanager.common.core.Event;

import java.time.Instant;
import java.util.UUID;

public abstract class JsonEventPayload<E extends Event> {

    private final UUID eventId;
    private final Instant occurrenceDate;

    protected JsonEventPayload(E event) {
        this.eventId = event.getEventId().getValue();
        this.occurrenceDate = event.getOccurrenceDate().getValue();
    }

    UUID getEventId() {
        return eventId;
    }

    Instant getOccurrenceDate() {
        return occurrenceDate;
    }
}

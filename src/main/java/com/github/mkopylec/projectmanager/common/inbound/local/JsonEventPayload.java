package com.github.mkopylec.projectmanager.common.inbound.local;

import java.time.Instant;
import java.util.UUID;

public abstract class JsonEventPayload {

    private final UUID eventId;
    private final Instant occurrenceDate;

    protected JsonEventPayload(UUID eventId, Instant occurrenceDate) {
        this.eventId = eventId;
        this.occurrenceDate = occurrenceDate;
    }

    public UUID getEventId() {
        return eventId;
    }

    public Instant getOccurrenceDate() {
        return occurrenceDate;
    }
}

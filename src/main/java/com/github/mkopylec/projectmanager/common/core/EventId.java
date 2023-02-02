package com.github.mkopylec.projectmanager.common.core;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class EventId extends Value<UUID> {

    EventId() {
        super(randomUUID());
    }
}

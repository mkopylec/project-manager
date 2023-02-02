package com.github.mkopylec.projectmanager.common.outbound.local;

import com.github.mkopylec.projectmanager.common.core.Event;

record JsonEvent(String marker, String type, JsonEventPayload<?> payload) {

    JsonEvent(Class<? extends Event> type, JsonEventPayload<?> payload) {
        this(JsonEvent.class.getName(), type.getSimpleName(), payload);
    }
}

package com.github.mkopylec.projectmanager.common.inbound.local;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class EventHandler<P extends JsonEventPayload> {

    private final Class<P> supportedPayload;
    private final ObjectMapper jsonMapper;

    protected EventHandler(Class<P> supportedPayload, ObjectMapper jsonMapper) {
        this.supportedPayload = supportedPayload;
        this.jsonMapper = jsonMapper;
    }

    protected abstract void handle(P event);

    void handleEvent(JsonEvent event) throws JsonProcessingException {
        if (event.type().equals(supportedPayload.getSimpleName())) {
            var payload = jsonMapper.convertValue(event.payload(), supportedPayload);
            handle(payload);
        }
    }
}

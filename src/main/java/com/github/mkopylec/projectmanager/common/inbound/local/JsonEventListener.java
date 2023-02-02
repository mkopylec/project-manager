package com.github.mkopylec.projectmanager.common.inbound.local;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!unit-tests")
class JsonEventListener {

    private final ObjectMapper jsonMapper;
    private final List<EventHandler<?>> eventHandlers;

    private JsonEventListener(ObjectMapper jsonMapper, List<EventHandler<?>> eventHandlers) {
        this.jsonMapper = jsonMapper;
        this.eventHandlers = eventHandlers;
    }

    @Async
    @EventListener(condition = "#event.contains(\"com.github.mkopylec.projectmanager.common.outbound.local.JsonEvent\")")
    void listen(String event) { // TODO Can be private?
        try {
            var jsonEvent = jsonMapper.readValue(event, JsonEvent.class);
            for (var handler : eventHandlers) {
                handler.handleEvent(jsonEvent);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error listening to " + event + " event", e);
        }
    }
}

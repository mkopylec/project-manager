package com.github.mkopylec.projectmanager.common.outbound.local;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkopylec.projectmanager.common.core.Event;
import com.github.mkopylec.projectmanager.common.core.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class JsonEventPublisher extends EventPublisher {

    private final ObjectMapper jsonMapper;
    private final ApplicationEventPublisher publisher;
    private final List<EventConverter<?>> eventConverters;

    private JsonEventPublisher(ObjectMapper jsonMapper, ApplicationEventPublisher publisher, List<EventConverter<?>> eventConverters) {
        this.jsonMapper = jsonMapper;
        this.publisher = publisher;
        this.eventConverters = eventConverters;
    }

    @Override
    protected void publish(Event event) {
        var payload = eventConverters.stream()
                .map(it -> it.convertEvent(event))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Error publishing " + event + " event. No " + EventConverter.class + " supports the event."));
        try {
            var jsonEvent = new JsonEvent(event.getClass(), payload);
            publisher.publishEvent(jsonMapper.writeValueAsString(jsonEvent));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error publishing " + event + " event", e);
        }
    }
}

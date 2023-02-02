package com.github.mkopylec.projectmanager.common.utils.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.event.EventListener

abstract class PublishedEvents {

    private final ObjectMapper jsonMapper
    private final List<Class<? extends JsonEventPayload>> allowedTypes
    private final List<? extends JsonEventPayload> payloads = []

    protected PublishedEvents(List<Class<? extends JsonEventPayload>> allowedTypes, ObjectMapper jsonMapper) {
        this.allowedTypes = allowedTypes
        this.jsonMapper = jsonMapper
    }

    @EventListener(condition = '#event.contains("com.github.mkopylec.projectmanager.common.outbound.local.JsonEvent")')
    private void handle(String event) { // TODO Private?
        def jsonEvent = jsonMapper.readValue(event, JsonEvent)
        def type = allowedTypes.find { it.simpleName == jsonEvent.type }
        if (type) {
            def payload = jsonMapper.convertValue(jsonEvent.payload, type)
            payloads.add(payload)
        }
    }

    protected <E extends JsonEventPayload> E get(Class<E> type) {
        payloads.find { it.class == type } as E
    }

    void clear() {
        payloads.clear()
    }
}

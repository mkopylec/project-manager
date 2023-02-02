package com.github.mkopylec.projectmanager.common.outbound.local;

import com.github.mkopylec.projectmanager.common.core.Event;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public abstract class EventConverter<E extends Event> {

    private final Class<E> supportedEvent;

    protected EventConverter(Class<E> supportedEvent) {
        this.supportedEvent = supportedEvent;
    }

    protected abstract JsonEventPayload<E> convert(E event);

    @SuppressWarnings("unchecked")
    Optional<JsonEventPayload<E>> convertEvent(Event event) {
        if (event.getClass().equals(supportedEvent)) {
            return of(convert((E) event));
        }
        return empty();
    }
}

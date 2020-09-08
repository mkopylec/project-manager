package com.github.mkopylec.projectmanager.infrastructure.services;

import com.github.mkopylec.projectmanager.core.common.Event;
import com.github.mkopylec.projectmanager.core.common.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.ThreadLocal.withInitial;

/**
 * Secondary adapter
 */
@Component
class SpringEventPublisher extends EventPublisher {

    private ApplicationEventPublisher publisher;
    private ThreadLocal<List<Event>> eventsToPublish = withInitial(ArrayList::new);

    SpringEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(Event event) {
        eventsToPublish.get().add(event);
    }

    @Override
    public void commit() {
        eventsToPublish.get().forEach(event -> publisher.publishEvent(event));
    }

    @Override
    public void dispose() {
        eventsToPublish.remove();
    }
}

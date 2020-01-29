package com.github.mkopylec.projectmanager.infrastructure.services;

import com.github.mkopylec.projectmanager.core.common.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringEventPublisher extends EventPublisher {

    private ApplicationEventPublisher publisher;

    SpringEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}

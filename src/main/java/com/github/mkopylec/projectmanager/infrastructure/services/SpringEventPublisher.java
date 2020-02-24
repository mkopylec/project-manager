package com.github.mkopylec.projectmanager.infrastructure.services;

import com.github.mkopylec.projectmanager.core.project.EndedProject;
import com.github.mkopylec.projectmanager.core.project.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Secondary adapter
 */
@Component
class SpringEventPublisher extends EventPublisher {

    private ApplicationEventPublisher publisher;

    SpringEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    protected void publish(EndedProject event) {
        publisher.publishEvent(event);
    }
}

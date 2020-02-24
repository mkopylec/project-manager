package com.github.mkopylec.projectmanager.core.project;

/**
 * Secondary port
 */
public abstract class EventPublisher {

    protected abstract void publish(EndedProject event);
}

package com.github.mkopylec.projectmanager.core.common;

/**
 * Secondary port
 */
public abstract class EventPublisher implements Committable {

    public abstract void publish(Event event);
}

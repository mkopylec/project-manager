package com.github.mkopylec.projectmanager.common.core;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class Aggregate<ID> extends Entity<ID> {

    private final AggregateStateVersion stateVersion;
    private final List<Event> raisedEvents = new ArrayList<>();

    protected Aggregate(ID id, AggregateStateVersion stateVersion) {
        super(id);
        this.stateVersion = requireNonNull(stateVersion, "No aggregate state version");
    }

    public AggregateStateVersion getStateVersion() {
        return stateVersion;
    }

    protected void raiseEvent(Event event) {
        raisedEvents.add(event);
    }

    void publishRaisedEvents(EventPublisher publisher) {
        raisedEvents.forEach(publisher::publish);
    }
}

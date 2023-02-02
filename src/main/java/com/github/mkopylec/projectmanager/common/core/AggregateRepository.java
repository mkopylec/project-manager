package com.github.mkopylec.projectmanager.common.core;

import java.util.Optional;
import java.util.function.Function;

public abstract class AggregateRepository<A extends Aggregate<ID>, ID> {

    protected void save(A aggregate, EventPublisher publisher, Function<Exception, ? extends BusinessRuleViolation> onConcurrentModification) {
        save(aggregate, onConcurrentModification);
        aggregate.publishRaisedEvents(publisher);
    }

    protected abstract Optional<A> find(ID id);

    protected abstract void save(A aggregate, Function<Exception, ? extends BusinessRuleViolation> onConcurrentModification);
}

package com.github.mkopylec.projectmanager.common.core;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;

public class AggregateStateVersion extends Value<Integer> {

    private AggregateStateVersion(Integer value) {
        super(value);
    }

    public AggregateStateVersion() {
        this(null);
    }

    public static AggregateStateVersion fromPersistentState(Integer value) {
        return requireNoBusinessRuleViolation(() -> new AggregateStateVersion(value));
    }
}

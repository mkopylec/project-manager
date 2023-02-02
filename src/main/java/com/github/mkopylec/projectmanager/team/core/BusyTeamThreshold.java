package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static java.lang.Math.max;

public class BusyTeamThreshold extends Value<Integer> {

    private static final int DEFAULT = 1;

    BusyTeamThreshold(Integer value) {
        super(value != null ? max(value, DEFAULT) : null);
    }

    public static BusyTeamThreshold fromPersistentState(Integer value) {
        return requireNoBusinessRuleViolation(() -> new BusyTeamThreshold(value));
    }

    static final class InvalidBusyTeamThreshold extends TeamBusinessRuleViolation {

        private InvalidBusyTeamThreshold(String threshold) {
            super(properties("threshold", threshold));
        }
    }
}

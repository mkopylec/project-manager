package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static java.lang.Math.max;

public class TeamCurrentlyImplementedProjects extends Value<Integer> {

    private static final int DEFAULT = 0;

    private TeamCurrentlyImplementedProjects(Integer value) {
        super(value != null ? max(value, DEFAULT) : null);
    }

    TeamCurrentlyImplementedProjects() {
        this(DEFAULT);
    }

    public static TeamCurrentlyImplementedProjects fromPersistentState(Integer value) {
        return requireNoBusinessRuleViolation(() -> new TeamCurrentlyImplementedProjects(value));
    }

    TeamCurrentlyImplementedProjects addProject() {
        return new TeamCurrentlyImplementedProjects(getValue() + 1);
    }

    TeamCurrentlyImplementedProjects removeProject() {
        return new TeamCurrentlyImplementedProjects(getValue() - 1);
    }

    boolean exceeds(BusyTeamThreshold threshold) {
        return getValue() > threshold.getValue();
    }

    static final class InvalidTeamCurrentlyImplementedProjects extends TeamBusinessRuleViolation {

        private InvalidTeamCurrentlyImplementedProjects(String currentlyImplementedProjects) {
            super(properties("currentlyImplementedProjects", currentlyImplementedProjects));
        }
    }
}

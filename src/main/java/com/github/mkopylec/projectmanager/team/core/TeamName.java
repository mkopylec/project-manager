package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static com.github.mkopylec.projectmanager.common.support.StringUtils.isBlank;

public class TeamName extends Value<String> {

    TeamName(String value) {
        super(value);
        if (isBlank(value)) {
            throw new InvalidTeamName(value);
        }
    }

    public static TeamName fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> new TeamName(value));
    }

    static final class InvalidTeamName extends TeamBusinessRuleViolation {

        private InvalidTeamName(String name) {
            super(properties("name", name));
        }
    }
}

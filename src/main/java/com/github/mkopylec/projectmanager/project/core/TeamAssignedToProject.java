package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static com.github.mkopylec.projectmanager.common.support.StringUtils.isBlank;

public class TeamAssignedToProject extends Value<String> {

    TeamAssignedToProject(String value) {
        super(value);
    }

    TeamAssignedToProject() {
        this(null);
    }

    void requireNotEmpty() {
        if (isBlank(getValue())) {
            throw new NoTeamAssignedToProject(getValue());
        }
    }

    public static TeamAssignedToProject fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> new TeamAssignedToProject(value));
    }

    static final class NoTeamAssignedToProject extends ProjectBusinessRuleViolation {

        private NoTeamAssignedToProject(String assignedTeam) {
            super(properties("assignedTeam", assignedTeam));
        }
    }
}

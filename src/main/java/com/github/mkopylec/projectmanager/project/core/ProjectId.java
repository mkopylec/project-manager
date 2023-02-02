package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import java.util.UUID;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static java.util.UUID.randomUUID;

public class ProjectId extends Value<UUID> {

    ProjectId() {
        this(randomUUID());
    }

    ProjectId(UUID value) {
        super(value);
        if (value == null) {
            throw new InvalidProjectId(value);
        }
    }

    public static ProjectId fromPersistentState(UUID value) {
        return requireNoBusinessRuleViolation(() -> new ProjectId(value));
    }

    static final class InvalidProjectId extends ProjectBusinessRuleViolation {

        private InvalidProjectId(UUID id) {
            super(properties("id", id));
        }
    }
}

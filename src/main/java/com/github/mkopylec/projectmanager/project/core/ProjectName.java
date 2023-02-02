package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static com.github.mkopylec.projectmanager.common.support.StringUtils.isBlank;

public class ProjectName extends Value<String> {

    ProjectName(String value) {
        super(value);
        if (isBlank(value)) {
            throw new InvalidProjectName(value);
        }
    }

    public static ProjectName fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> new ProjectName(value));
    }

    static final class InvalidProjectName extends ProjectBusinessRuleViolation {

        private InvalidProjectName(String name) {
            super(properties("name", name));
        }
    }
}

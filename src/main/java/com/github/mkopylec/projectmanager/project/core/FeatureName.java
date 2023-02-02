package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static com.github.mkopylec.projectmanager.common.support.StringUtils.isBlank;

public class FeatureName extends Value<String> {

    FeatureName(String value) {
        super(value);
        if (isBlank(value)) {
            throw new InvalidFeatureName(value);
        }
    }

    public static FeatureName fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> new FeatureName(value));
    }

    static final class InvalidFeatureName extends ProjectBusinessRuleViolation {

        private InvalidFeatureName(String name) {
            super(properties("name", name));
        }
    }
}

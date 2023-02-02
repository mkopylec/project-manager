package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;

public abstract sealed class FeatureRequirement extends Value<String> {

    private FeatureRequirement(String value) {
        super(value);
    }

    public static FeatureRequirement fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> switch (value) {
            case Optional.OPTIONAL -> new Optional();
            case Recommended.RECOMMENDED -> new Recommended();
            case Necessary.NECESSARY -> new Necessary();
            default -> throw new InvalidFeatureRequirement(value);
        });
    }

    static final class Optional extends FeatureRequirement {

        private static final String OPTIONAL = "Optional";

        Optional() {
            super(OPTIONAL);
        }
    }

    static final class Recommended extends FeatureRequirement {

        private static final String RECOMMENDED = "Recommended";

        Recommended() {
            super(RECOMMENDED);
        }
    }

    static final class Necessary extends FeatureRequirement {

        private static final String NECESSARY = "Necessary";

        Necessary() {
            super(NECESSARY);
        }
    }

    static final class InvalidFeatureRequirement extends ProjectBusinessRuleViolation {

        private InvalidFeatureRequirement(String requirement) {
            super(properties("requirement", requirement));
        }
    }
}

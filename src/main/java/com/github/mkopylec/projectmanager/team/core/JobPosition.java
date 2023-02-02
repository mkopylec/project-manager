package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;

public abstract sealed class JobPosition extends Value<String> {

    private JobPosition(String value) {
        super(value);
    }

    public static JobPosition fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> switch (value) {
            case SoftwareDeveloper.SOFTWARE_DEVELOPER -> new SoftwareDeveloper();
            case ScrumMaster.SCRUM_MASTER -> new ScrumMaster();
            case ProductOwner.PRODUCT_OWNER -> new ProductOwner();
            default -> throw new InvalidJobPosition(value);
        });
    }

    static final class SoftwareDeveloper extends JobPosition {

        private static final String SOFTWARE_DEVELOPER = "Software developer";

        SoftwareDeveloper() {
            super(SOFTWARE_DEVELOPER);
        }
    }

    static final class ScrumMaster extends JobPosition {

        private static final String SCRUM_MASTER = "Scrum master";

        ScrumMaster() {
            super(SCRUM_MASTER);
        }
    }

    static final class ProductOwner extends JobPosition {

        private static final String PRODUCT_OWNER = "Product owner";

        ProductOwner() {
            super(PRODUCT_OWNER);
        }
    }

    static final class InvalidJobPosition extends TeamBusinessRuleViolation {

        private InvalidJobPosition(String position) {
            super(properties("position", position));
        }
    }
}

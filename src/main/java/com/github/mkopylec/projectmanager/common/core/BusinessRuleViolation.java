package com.github.mkopylec.projectmanager.common.core;

import java.util.function.Supplier;

public abstract class BusinessRuleViolation extends RuntimeException {

    private final BusinessRuleViolationProperties properties;

    protected BusinessRuleViolation(BusinessRuleViolationProperties properties) {
        super(properties.toString());
        this.properties = properties;
    }

    protected BusinessRuleViolation(BusinessRuleViolationProperties properties, Exception cause) {
        super(properties.toString(), cause);
        this.properties = properties;
    }

    BusinessRuleViolationProperties getProperties() {
        return properties;
    }

    public static <T> T requireNoBusinessRuleViolation(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (BusinessRuleViolation violation) {
            throw new IllegalStateException(violation.getMessage(), violation);
        }
    }
}

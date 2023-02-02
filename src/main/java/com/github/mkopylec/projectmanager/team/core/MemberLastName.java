package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static com.github.mkopylec.projectmanager.common.support.StringUtils.isBlank;

public class MemberLastName extends Value<String> {

    MemberLastName(String value) {
        super(value);
        if (isBlank(value)) {
            throw new InvalidEmployeeLastName(value);
        }
    }

    public static MemberLastName fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> new MemberLastName(value));
    }

    static final class InvalidEmployeeLastName extends TeamBusinessRuleViolation {

        private InvalidEmployeeLastName(String lastName) {
            super(properties("lastName", lastName));
        }
    }
}

package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static com.github.mkopylec.projectmanager.common.support.StringUtils.isBlank;

public class MemberFirstName extends Value<String> {

    MemberFirstName(String value) {
        super(value);
        if (isBlank(value)) {
            throw new InvalidEmployeeFirstName(value);
        }
    }

    public static MemberFirstName fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> new MemberFirstName(value));
    }

    static final class InvalidEmployeeFirstName extends TeamBusinessRuleViolation {

        private InvalidEmployeeFirstName(String firstName) {
            super(properties("firstName", firstName));
        }
    }
}

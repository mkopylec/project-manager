package com.github.mkopylec.projectmanager.core.project;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.INVALID_REQUIREMENT;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isValueOfEnum;

public enum Requirement {

    OPTIONAL,
    RECOMMENDED,
    NECESSARY;

    static Requirement from(String value) {
        if (isEmpty(value)) {
            return null;
        }
        require(isValueOfEnum(value, Requirement.class), INVALID_REQUIREMENT);
        return valueOf(value);
    }
}

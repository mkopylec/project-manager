package com.github.mkopylec.projectmanager.core.team;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.INVALID_JOB_POSITION;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isValueOfEnum;

public enum JobPosition {

    DEVELOPER,
    SCRUM_MASTER,
    PRODUCT_OWNER;

    static JobPosition from(String value) {
        if (isEmpty(value)) {
            return null;
        }
        require(isValueOfEnum(value, JobPosition.class), INVALID_JOB_POSITION);
        return valueOf(value);
    }
}

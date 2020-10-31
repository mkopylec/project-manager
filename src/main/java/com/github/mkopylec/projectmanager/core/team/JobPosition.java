package com.github.mkopylec.projectmanager.core.team;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isValueOfEnum;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.INVALID_JOB_POSITION;

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

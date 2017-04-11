package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.EnumCreator.createEnum;

public enum JobPosition {

    _INVALID,
    DEVELOPER,
    SCRUM_MASTER,
    PRODUCT_OWNER;

    public static JobPosition createJobPosition(String jobPosition) {
        return createEnum(JobPosition.class, jobPosition, _INVALID);
    }
}

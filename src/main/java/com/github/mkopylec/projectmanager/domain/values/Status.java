package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.EnumCreator.createEnum;

public enum Status {

    _INVALID,
    TO_DO,
    IN_PROGRESS,
    DONE;

    public static Status createStatus(String status) {
        return createEnum(Status.class, status, _INVALID);
    }
}

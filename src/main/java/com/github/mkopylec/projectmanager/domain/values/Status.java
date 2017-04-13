package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.EnumCreator.createEnum;

public enum Status {

    _INVALID,
    TO_DO,
    IN_PROGRESS,
    DONE;

    public boolean isAtLeastStarted() {
        return this != TO_DO;
    }

    public static Status createStatus(String status) {
        return createEnum(Status.class, status, _INVALID);
    }
}

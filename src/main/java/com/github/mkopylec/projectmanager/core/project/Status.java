package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.InvalidStateSupportingEnum;

enum Status implements InvalidStateSupportingEnum {

    TO_DO,
    IN_PROGRESS,
    DONE,
    INVALID;

    boolean isAtLeastStarted() {
        return this != TO_DO;
    }

    boolean isNotStarted() {
        return this == TO_DO;
    }

    boolean isDone() {
        return this == DONE;
    }
}

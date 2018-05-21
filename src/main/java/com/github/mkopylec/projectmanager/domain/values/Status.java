package com.github.mkopylec.projectmanager.domain.values;

public enum Status {

    TO_DO,
    IN_PROGRESS,
    DONE,
    INVALID;

    public boolean isAtLeastStarted() {
        return this != TO_DO;
    }

    public boolean isNotStarted() {
        return this == TO_DO;
    }

    public boolean isDone() {
        return this == DONE;
    }
}

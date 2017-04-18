package com.github.mkopylec.projectmanager.domain.values;

public enum Status {

    TO_DO,
    IN_PROGRESS,
    DONE;

    public boolean isAtLeastStarted() {
        return this != TO_DO;
    }
}

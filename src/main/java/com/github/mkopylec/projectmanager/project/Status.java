package com.github.mkopylec.projectmanager.project;

public enum Status {

    TO_DO,
    IN_PROGRESS,
    DONE;

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

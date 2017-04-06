package com.github.mkopylec.projectmanager.domain.exceptions;

public class PreCondition {

    private final boolean condition;

    private PreCondition(boolean condition) {
        this.condition = condition;
    }

    public static PreCondition when(boolean condition) {
        return new PreCondition(condition);
    }

    public void thenInvalidEntity(ErrorCode code, String message) {
        if (condition) {
            throw new InvalidEntityException(message, code);
        }
    }

    public void thenInvalidValue(ErrorCode code, String message) {
        if (condition) {
            throw new InvalidValueException(message, code);
        }
    }

    public void thenEntityAlreadyExists(ErrorCode code, String message) {
        if (condition) {
            throw new EntityAlreadyExistsException(message, code);
        }
    }

    public void thenMissingEntity(ErrorCode code, String message) {
        if (condition) {
            throw new MissingEntityException(message, code);
        }
    }
}

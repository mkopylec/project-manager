package com.github.mkopylec.projectmanager.domain.exceptions;

import java.util.function.Supplier;

public class PreCondition {

    private boolean condition;

    private PreCondition(boolean condition) {
        this.condition = condition;
    }

    public static PreCondition when(boolean condition) {
        return new PreCondition(condition);
    }

    public void thenInvalidEntity(ErrorCode code, String message) {
        thenThrow(() -> new InvalidEntityException(message, code));
    }

    public void thenEntityAlreadyExists(ErrorCode code, String message) {
        thenThrow(() -> new EntityAlreadyExistsException(message, code));
    }

    public void thenMissingEntity(ErrorCode code, String message) {
        thenThrow(() -> new MissingEntityException(message, code));
    }

    private void thenThrow(Supplier<DomainException> exceptionCreator) {
        if (condition) {
            throw exceptionCreator.get();
        }
    }
}
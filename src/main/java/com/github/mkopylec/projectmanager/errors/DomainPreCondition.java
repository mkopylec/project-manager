package com.github.mkopylec.projectmanager.errors;

import java.util.function.Supplier;

public abstract class DomainPreCondition {

    private boolean condition;

    protected DomainPreCondition(boolean condition) {
        this.condition = condition;
    }

    protected void thenThrow(Supplier<DomainException> exceptionCreator) {
        if (condition) {
            throw exceptionCreator.get();
        }
    }
}

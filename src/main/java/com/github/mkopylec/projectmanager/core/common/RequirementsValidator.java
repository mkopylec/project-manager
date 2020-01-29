package com.github.mkopylec.projectmanager.core.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class RequirementsValidator {

    private List<ValidationError> errors = new ArrayList<>();
    private Function<List<ValidationError>, ? extends ProjectManagerException> exceptionCreator;

    protected RequirementsValidator(Function<List<ValidationError>, ? extends ProjectManagerException> exceptionCreator) {
        this.exceptionCreator = exceptionCreator;
    }

    protected final void addError(Enum<? extends ValidationErrorCode> code, String message) {
        errors.add(new ValidationError(code, message));
    }

    public final void validate() {
        if (!errors.isEmpty()) {
            throw exceptionCreator.apply(errors);
        }
    }
}

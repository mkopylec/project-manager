package com.github.mkopylec.projectmanager.core.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class RequirementsValidator {

    private List<Enum<? extends ValidationErrorCode>> errorCodes = new ArrayList<>();
    private Function<List<Enum<? extends ValidationErrorCode>>, ? extends RequirementsValidationException> exceptionCreator;

    protected RequirementsValidator(Function<List<Enum<? extends ValidationErrorCode>>, ? extends RequirementsValidationException> exceptionCreator) {
        this.exceptionCreator = exceptionCreator;
    }

    protected final void addError(Enum<? extends ValidationErrorCode> code) {
        errorCodes.add(code);
    }

    public final void validate() {
        if (!errorCodes.isEmpty()) {
            throw exceptionCreator.apply(errorCodes);
        }
    }
}

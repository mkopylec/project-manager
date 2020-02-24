package com.github.mkopylec.projectmanager.core.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isNotEmpty;

public abstract class Validator<C extends Enum<? extends ValidationErrorCode>> {

    private List<C> errorCodes = new ArrayList<>();
    private Function<List<C>, ? extends ValidationException> exceptionCreator;

    protected Validator(Function<List<C>, ? extends ValidationException> exceptionCreator) {
        this.exceptionCreator = exceptionCreator;
    }

    @SuppressWarnings("unchecked")
    protected <V extends Validator<C>> V require(Object inputData, C code) {
        if (isEmpty(inputData)) {
            addError(code);
        }
        return (V) this;
    }

    @SuppressWarnings("unchecked")
    protected <V extends Validator<C>> V requireNo(Object inputData, C code) {
        if (isNotEmpty(inputData)) {
            addError(code);
        }
        return (V) this;
    }

    protected void addError(C code) {
        errorCodes.add(code);
    }

    public final void validate() {
        if (!errorCodes.isEmpty()) {
            throw exceptionCreator.apply(errorCodes);
        }
    }
}

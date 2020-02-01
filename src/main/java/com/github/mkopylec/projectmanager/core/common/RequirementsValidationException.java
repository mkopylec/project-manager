package com.github.mkopylec.projectmanager.core.common;

import java.util.List;
import java.util.function.Function;

import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static java.util.Collections.unmodifiableList;

public abstract class RequirementsValidationException extends RuntimeException {

    private List<Enum<? extends ValidationErrorCode>> errorCodes;

    protected RequirementsValidationException(List<Enum<? extends ValidationErrorCode>> errorCodes) {
        this.errorCodes = unmodifiableList(errorCodes);
    }

    public <E> List<E> mapErrorCodes(Function<Enum<? extends ValidationErrorCode>, E> mapper) {
        return mapElements(errorCodes, mapper);
    }

    @Override
    public String getMessage() {
        return errorCodes.toString();
    }
}

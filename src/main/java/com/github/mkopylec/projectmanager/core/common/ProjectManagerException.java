package com.github.mkopylec.projectmanager.core.common;

import java.util.List;
import java.util.function.Function;

import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static java.util.Collections.unmodifiableList;

public abstract class ProjectManagerException extends RuntimeException {

    private Enum<? extends ValidationErrorCode> missingEntityCode;
    private List<ValidationError> validationErrors;

    protected ProjectManagerException(Enum<? extends ValidationErrorCode> missingEntityCode, List<ValidationError> validationErrors) {
        this.missingEntityCode = missingEntityCode;
        this.validationErrors = unmodifiableList(validationErrors);
    }

    public <E> List<E> mapValidationErrors(Function<ValidationError, E> mapper) {
        return mapElements(validationErrors, mapper);
    }

    public boolean indicatesMissingEntity() {
        return validationErrors.stream().anyMatch(error -> error.hasCode(missingEntityCode));
    }

    @Override
    public String getMessage() {
        return validationErrors.toString();
    }
}

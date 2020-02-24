package com.github.mkopylec.projectmanager.core.common;

import java.util.List;
import java.util.function.Function;

import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static java.util.Collections.emptyList;

public class ProjectManagerException extends RuntimeException {

    ProjectManagerException(String useCase, Throwable cause) {
        super("Use case '" + useCase + "' has failed", cause);
    }

    public <E> List<E> mapValidationErrorCodes(Function<Enum<? extends ValidationErrorCode>, E> mapper) {
        return mapElements(getFromValidationException(ValidationException::getErrorCodes, emptyList()), mapper);
    }

    public boolean indicatesMissingEntity() {
        return getFromValidationException(ValidationException::indicatesMissingEntity, false);
    }

    public boolean indicatesInvalidEntity() {
        return getFromValidationException(ValidationException::indicatesInvalidEntity, false);
    }

    public boolean indicatesError() {
        return !(getCause() instanceof ValidationException);
    }

    private <T> T getFromValidationException(Function<ValidationException, T> getter, T defaultValue) {
        if (getCause() instanceof ValidationException) {
            return getter.apply((ValidationException) getCause());
        }
        return defaultValue;
    }
}

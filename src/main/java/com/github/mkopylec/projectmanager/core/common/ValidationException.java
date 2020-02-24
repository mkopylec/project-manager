package com.github.mkopylec.projectmanager.core.common;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public abstract class ValidationException extends RuntimeException {

    private List<Enum<? extends ValidationErrorCode>> errorCodes;

    protected ValidationException(List<? extends Enum<? extends ValidationErrorCode>> errorCodes) {
        this.errorCodes = unmodifiableList(errorCodes);
    }

    @Override
    public String getMessage() {
        return errorCodes.toString();
    }

    protected boolean containsErrorCode(Enum<? extends ValidationErrorCode> errorCode) {
        return errorCodes.contains(errorCode);
    }

    protected abstract boolean indicatesMissingEntity();

    boolean indicatesInvalidEntity() {
        return !indicatesMissingEntity();
    }

    List<Enum<? extends ValidationErrorCode>> getErrorCodes() {
        return errorCodes;
    }
}

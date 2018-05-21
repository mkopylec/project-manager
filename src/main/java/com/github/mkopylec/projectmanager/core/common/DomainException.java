package com.github.mkopylec.projectmanager.core.common;

public abstract class DomainException extends RuntimeException {

    private ErrorMessage errorMessage;

    public DomainException(String message, Enum<?> code) {
        super(message);
        this.errorMessage = new ErrorMessage(code.name());
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}

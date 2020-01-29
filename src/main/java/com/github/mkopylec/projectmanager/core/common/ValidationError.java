package com.github.mkopylec.projectmanager.core.common;

public class ValidationError {

    private Enum<? extends ValidationErrorCode> code;
    private String message;

    ValidationError(Enum<? extends ValidationErrorCode> code, String message) {
        this.code = code;
        this.message = message;
    }

    public Enum<? extends ValidationErrorCode> getCode() {
        return code;
    }

    public boolean hasCode(Enum<? extends ValidationErrorCode> code) {
        return this.code == code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
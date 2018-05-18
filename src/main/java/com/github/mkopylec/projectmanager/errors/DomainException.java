package com.github.mkopylec.projectmanager.errors;

public abstract class DomainException extends RuntimeException {

    private String code;

    public DomainException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

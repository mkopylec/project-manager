package com.github.mkopylec.projectmanager.api.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class InvalidEntityException extends ApplicationException {

    public static void require(boolean condition, ApplicationViolation violation) {
        if (!condition) {
            throw new InvalidEntityException(violation);
        }
    }

    private InvalidEntityException(ApplicationViolation violation) {
        super(violation, UNPROCESSABLE_ENTITY);
    }
}

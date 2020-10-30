package com.github.mkopylec.projectmanager.api.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MissingEntityException extends ApplicationException {

    public static void requireExisting(Object entity, ApplicationViolation violation) {
        if (entity == null) {
            throw new MissingEntityException(violation);
        }
    }

    private MissingEntityException(ApplicationViolation violation) {
        super(violation, NOT_FOUND);
    }
}

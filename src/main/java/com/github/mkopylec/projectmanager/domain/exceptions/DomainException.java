package com.github.mkopylec.projectmanager.domain.exceptions;

public class DomainException extends RuntimeException {

    private final ErrorCode code;

    DomainException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }

    public static Condition when(boolean condition) {
        return new Condition(condition);
    }

    public static class Condition {

        private final boolean condition;

        Condition(boolean condition) {
            this.condition = condition;
        }

        public void thenThrow(ErrorCode code, String message) {
            if (condition) {
                throw new DomainException(message, code);
            }
        }
    }
}

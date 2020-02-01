package com.github.mkopylec.projectmanager.core;

import java.util.List;
import java.util.stream.Stream;

public class Failure {

    private static final String UNEXPECTED_ERROR_CODE = "UNEXPECTED_ERROR";

    private List<String> codes;
    private String message;

    Failure() {
    }

    public List<String> getCodes() {
        return codes;
    }

    public boolean containsAnyCode(String... codes) {
        return Stream.of(codes).anyMatch(code -> this.codes.contains(code));
    }

    public boolean containsUnexpectedErrorCode() {
        return codes.contains(UNEXPECTED_ERROR_CODE);
    }

    Failure setUnexpectedErrorCode() {
        codes = List.of(UNEXPECTED_ERROR_CODE);
        return this;
    }

    Failure setCodes(List<String> codes) {
        this.codes = codes;
        return this;
    }

    public String getMessage() {
        return message;
    }

    Failure setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return message + ": " + codes;
    }
}

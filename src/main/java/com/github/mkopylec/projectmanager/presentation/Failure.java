package com.github.mkopylec.projectmanager.presentation;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Failure {

    private List<String> codes = List.of("UNEXPECTED_ERROR");
    private String message;

    Failure(List<String> codes, String message) {
        if (!codes.isEmpty()) {
            this.codes = unmodifiableList(codes);
        }
        this.message = message;
    }

    public List<String> getCodes() {
        return codes;
    }

    public String getMessage() {
        return message;
    }

    private Failure() {
    }
}

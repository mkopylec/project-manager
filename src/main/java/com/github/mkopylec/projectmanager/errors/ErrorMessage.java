package com.github.mkopylec.projectmanager.errors;

class ErrorMessage {

    private String code;

    ErrorMessage(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
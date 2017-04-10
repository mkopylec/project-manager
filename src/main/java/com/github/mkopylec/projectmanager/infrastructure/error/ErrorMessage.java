package com.github.mkopylec.projectmanager.infrastructure.error;

class ErrorMessage {

    private String code;

    ErrorMessage(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

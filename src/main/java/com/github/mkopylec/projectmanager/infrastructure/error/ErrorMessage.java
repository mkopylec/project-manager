package com.github.mkopylec.projectmanager.infrastructure.error;

import com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode;

class ErrorMessage {

    private ErrorCode code;

    ErrorMessage(ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
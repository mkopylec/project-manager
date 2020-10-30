package com.github.mkopylec.projectmanager.utils

import com.github.mkopylec.projectmanager.api.dto.Failure
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.valueOf

class HttpResponse<B> {

    private HttpStatus status
    private B body
    private Failure failure

    protected HttpResponse(int status, B body, Failure failure) {
        this.status = valueOf(status)
        this.body = body
        this.failure = failure
    }

    HttpStatus getStatus() {
        status
    }

    B getBody() {
        body
    }

    Failure getFailure() {
        failure
    }
}

package com.github.mkopylec.projectmanager.utils

import com.github.mkopylec.projectmanager.presentation.Failure
import org.springframework.http.HttpStatus

class HttpResponse<B> {

    private HttpStatus status
    private B body
    private Failure failure

    protected HttpResponse(HttpStatus status, B body, Failure failure) {
        this.status = status
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

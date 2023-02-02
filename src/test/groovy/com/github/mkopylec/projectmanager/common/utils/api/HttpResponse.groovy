package com.github.mkopylec.projectmanager.common.utils.api

import com.github.mkopylec.projectmanager.common.inbound.http.FailureResponseBody
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

import static org.springframework.http.HttpStatus.valueOf

class HttpResponse<B, F extends FailureResponseBody> {

    private HttpStatusCode status
    private B body
    private F failure

    protected HttpResponse(HttpStatusCode status, B body, F failure) {
        this.status = status
        this.body = body
        this.failure = failure
    }

    HttpStatus getStatus() {
        valueOf(status.value())
    }

    B getBody() {
        body
    }

    F getFailure() {
        failure
    }
}

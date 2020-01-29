package com.github.mkopylec.projectmanager.utils

import com.github.mkopylec.projectmanager.presentation.Error
import org.springframework.http.HttpStatus

class HttpResponse<B> {

    private HttpStatus status
    private B body
    private List<Error> errors

    protected HttpResponse(HttpStatus status, B body, List<Error> errors) {
        this.status = status
        this.body = body
        this.errors = errors
    }

    HttpStatus getStatus() {
        return status
    }

    B getBody() {
        return body
    }

    List<Error> getErrors() {
        return errors
    }
}

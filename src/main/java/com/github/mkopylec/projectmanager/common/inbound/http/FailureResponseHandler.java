package com.github.mkopylec.projectmanager.common.inbound.http;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.status;

public abstract class FailureResponseHandler {

    private static final Logger logger = getLogger(FailureResponseHandler.class);

    protected <F extends FailureResponseBody<?>> ResponseEntity<F> handle(F body, Exception exception, HttpServletRequest request, HttpStatus status) {
        var response = status(status).body(body);
        var log = createLog(request, response);
        if (status.is5xxServerError()) {
            logger.error(log, exception);
        } else {
            logger.warn(log, exception);
        }
        return response;
    }

    private String createLog(HttpServletRequest request, ResponseEntity<? extends FailureResponseBody<?>> response) {
        return request.getMethod() + " " + request.getRequestURI() + " " + response.getStatusCode().value() + " | " + response.getBody();
    }
}

package com.github.mkopylec.projectmanager.common.utils.api

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity

import static java.net.URI.create
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.RequestEntity.method

class HttpRequest {

    private HttpMethod method
    private URI url
    private HttpHeaders headers = new HttpHeaders()
    private Object body

    HttpRequest setMethod(HttpMethod method) {
        this.method = method
        this
    }

    HttpRequest setUrl(String url) {
        this.url = create(url)
        this
    }

    HttpRequest setBody(Object body) {
        this.body = body
        this
    }

    RequestEntity<Object> toRequestEntity() {
        headers.setAccept([APPLICATION_JSON])
        if (body) {
            headers.setContentType(APPLICATION_JSON)
        }
        method(method, url)
                .headers(headers)
                .body(body)
    }
}

package com.github.mkopylec.projectmanager.common.utils.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mkopylec.projectmanager.common.inbound.http.FailureResponseBody
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory

abstract class HttpClient {

    private TestRestTemplate httpClient
    private HttpResponseBodyReader bodyReader
    private ObjectMapper mapper

    protected HttpClient(TestRestTemplate httpClient, HttpResponseBodyReader bodyReader, ObjectMapper mapper) {
        this.httpClient = httpClient.tap { it.restTemplate.requestFactory = new HttpComponentsClientHttpRequestFactory() }
        this.bodyReader = bodyReader
        this.mapper = mapper
    }

    protected <B, F extends FailureResponseBody> HttpResponse<B, F> sendRequest(HttpRequest httpRequest, Class<B> successResponseBody, Class<F> failureResponseBody) {
        def response = httpClient.exchange(httpRequest.toRequestEntity(), String)
        def body = bodyReader.getBody(response, successResponseBody)
        def failure = bodyReader.getBody(response, failureResponseBody)
        new HttpResponse<>(response.statusCode, body, failure)
    }
}

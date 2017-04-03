package com.github.mkopylec.projectmanager

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpMethod.PUT

@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class BasicSpec extends Specification {

    @Rule
    public WireMockRule reportingService = new WireMockRule(8081)
    @Autowired
    private TestRestTemplate restTemplate

    protected void stubReportingService() {
        stubFor(post(urlEqualTo('/reports/projects')).willReturn(aResponse().withStatus(201)))
    }

    protected <T> ResponseEntity<T> get(String uri, Class<T> responseBodyType) {
        return sendRequest(uri, GET, null, responseBodyType)
    }

    protected ResponseEntity post(String uri, Object requestBody) {
        return sendRequest(uri, POST, requestBody, Object)
    }

    protected <T> ResponseEntity<T> post(String uri, Object requestBody, Class<T> responseBodyType) {
        return sendRequest(uri, POST, requestBody, responseBodyType)
    }

    protected ResponseEntity put(String uri, Object requestBody) {
        return sendRequest(uri, PUT, requestBody, Object)
    }

    protected <T> ResponseEntity<T> put(String uri, Object requestBody, Class<T> responseBodyType) {
        return sendRequest(uri, PUT, requestBody, responseBodyType)
    }

    private <T> ResponseEntity<T> sendRequest(String uri, HttpMethod method, Object requestBody, Class<T> responseBodyType) {
        def entity = new HttpEntity<>(requestBody)
        return restTemplate.exchange(uri, method, entity, responseBodyType)
    }
}
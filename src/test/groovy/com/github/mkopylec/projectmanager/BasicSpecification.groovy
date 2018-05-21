package com.github.mkopylec.projectmanager

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.containing
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.verify
import static java.util.concurrent.TimeUnit.SECONDS
import static org.awaitility.Awaitility.await
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.PATCH
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpMethod.PUT

@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class BasicSpecification extends Specification {

    @Rule
    public WireMockRule reportingService = new WireMockRule(8081)
    @Autowired
    private TestRestTemplate restTemplate
    @Autowired
    private MongoTemplate mongo

    void setupSpec() {
        fixWireMock()
    }

    void setup() {
        clearMongoDb()
    }

    protected static void stubReportingService() {
        stubFor(post(urlEqualTo('/reports/projects')).willReturn(aResponse().withStatus(201)))
    }

    protected static void verifyReportWasSent(String projectIdentifier) {
        verifyReportSending(1, projectIdentifier)
    }

    protected static void verifyReportWasNotSent(String projectIdentifier) {
        verifyReportSending(0, projectIdentifier)
    }

    protected <T> ResponseEntity<T> get(String uri, Class<T> responseBodyType) {
        return sendRequest(uri, GET, null, responseBodyType)
    }

    protected <T> ResponseEntity<T> get(String uri, ParameterizedTypeReference<T> responseBodyType) {
        return sendRequest(uri, GET, null, responseBodyType)
    }

    protected ResponseEntity post(String uri, Object requestBody) {
        return sendRequest(uri, POST, requestBody, Object)
    }

    protected ResponseEntity put(String uri, Object requestBody) {
        return sendRequest(uri, PUT, requestBody, Object)
    }

    protected ResponseEntity patch(String uri) {
        return sendRequest(uri, PATCH, null, Object)
    }

    protected ResponseEntity patch(String uri, Object requestBody) {
        return sendRequest(uri, PATCH, requestBody, Object)
    }

    private <T> ResponseEntity<T> sendRequest(String uri, HttpMethod method, Object requestBody, Class<T> responseBodyType) {
        def entity = new HttpEntity<>(requestBody)
        return restTemplate.exchange(uri, method, entity, responseBodyType)
    }

    private <T> ResponseEntity<T> sendRequest(String uri, HttpMethod method, Object requestBody, ParameterizedTypeReference<T> responseBodyType) {
        def entity = new HttpEntity<>(requestBody)
        return restTemplate.exchange(uri, method, entity, responseBodyType)
    }

    private static void fixWireMock() {
        System.setProperty('http.keepAlive', 'false')
        System.setProperty('http.maxConnections', '1')
    }

    private void clearMongoDb() {
        for (def collection : mongo.collectionNames) {
            mongo.dropCollection(collection)
        }
    }

    private static void verifyReportSending(int count, String projectIdentifier) {
        await().atMost(1, SECONDS).untilAsserted {
            verify(count, postRequestedFor(urlEqualTo('/reports/projects')).withRequestBody(containing(projectIdentifier)))
        }
    }
}
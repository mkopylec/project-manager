package com.github.mkopylec.projectmanager.utils

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.containing
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static java.util.concurrent.TimeUnit.SECONDS
import static org.awaitility.Awaitility.await

class ReportingServiceStub implements MethodRule {

    private WireMockRule rule

    ReportingServiceStub() {
        rule = new WireMockRule(8081)
    }

    void stubReportReceiving() {
        rule.stubFor(post(urlEqualTo('/reports/projects')).willReturn(aResponse().withStatus(201)))
    }

    void verifyReportWasSent(String projectIdentifier) {
        verifyReportSending(1, projectIdentifier)
    }

    void verifyReportWasNotSent(String projectIdentifier) {
        verifyReportSending(0, projectIdentifier)
    }

    void clear() {
        rule.resetRequests()
    }

    private void verifyReportSending(int count, String projectIdentifier) {
        await().atMost(1, SECONDS).untilAsserted {
            rule.verify(count, postRequestedFor(urlEqualTo('/reports/projects')).withRequestBody(containing(projectIdentifier)))
        }
    }

    @Override
    Statement apply(Statement base, FrameworkMethod method, Object target) {
        rule.apply(base, method, target)
    }
}

package com.github.mkopylec.projectmanager.core.project;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import static org.slf4j.LoggerFactory.getLogger;

@Component
class EndedProjectsReporter {

    private static final Logger log = getLogger(EndedProjectsReporter.class);

    private RestOperations httpClient;

    EndedProjectsReporter(RestOperations httpClient) {
        this.httpClient = httpClient;
    }

    void report(EndedProject endedProject) {
        try {
            httpClient.postForObject("http://localhost:8081/reports/projects", endedProject, Void.class);
        } catch (RestClientException ex) {
            log.error("Error reporting ended project", ex);
        }
    }
}

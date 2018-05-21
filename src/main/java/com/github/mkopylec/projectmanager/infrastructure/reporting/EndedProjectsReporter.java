package com.github.mkopylec.projectmanager.infrastructure.reporting;

import com.github.mkopylec.projectmanager.core.project.EndedProject;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

@Component
class EndedProjectsReporter {

    private static final Logger log = getLogger(EndedProjectsReporter.class);

    private RestTemplate restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(200)
            .setReadTimeout(2000)
            .build();

    @Async
    @EventListener
    void report(EndedProject endedProject) {
        try {
            restTemplate.postForObject("http://localhost:8081/reports/projects", endedProject, String.class);
        } catch (RestClientException ex) {
            log.error("Error reporting ended project", ex);
        }
    }
}

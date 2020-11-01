package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.UnitOfWork;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

@Component
class EndedProjectsReporter extends UnitOfWork {

    private static final Logger log = getLogger(EndedProjectsReporter.class);

    private RestOperations httpClient;

    EndedProjectsReporter(HttpServletRequest request, RestOperations httpClient) {
        super(request);
        this.httpClient = httpClient;
    }

    void report(EndedProject endedProject) {
        addWriteOperation(() -> {
            try {
                httpClient.postForObject("http://localhost:8081/reports/projects", endedProject, Void.class);
            } catch (RestClientException ex) {
                log.error("Error reporting ended project", ex);
            }
        });
    }

    @Override
    public int getOrder() {
        return super.getOrder() - 1;
    }
}

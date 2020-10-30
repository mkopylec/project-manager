package com.github.mkopylec.projectmanager.utils;

import com.github.mkopylec.projectmanager.core.project.EndedProject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import static com.github.mkopylec.projectmanager.utils.Exactly.exactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Component
public class ReportingService {

    private RestOperations httpClient;

    public ReportingService(RestOperations httpClient) {
        this.httpClient = httpClient;
    }

    public void verifyReportWasSent(EndedProjectBuilder endedProject) {
        verify(httpClient, times(1)).postForObject(eq("http://localhost:8081/reports/projects"), exactly(endedProject.build()), eq(Void.class), any(Object.class));
    }

    public void verifyReportWasNotSent() {
        verify(httpClient, never()).postForObject(eq("http://localhost:8081/reports/projects"), any(EndedProject.class), eq(Void.class), any(Object.class));
    }
}

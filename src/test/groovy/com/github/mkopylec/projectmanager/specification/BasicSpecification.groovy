package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.utils.MongoDb
import com.github.mkopylec.projectmanager.utils.ProjectManagerHttpClient
import com.github.mkopylec.projectmanager.utils.ReportingServiceStub
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class BasicSpecification extends Specification {

    @Rule
    public ReportingServiceStub reportingService = new ReportingServiceStub()
    @Autowired
    protected ProjectManagerHttpClient httpClient
    @Autowired
    private MongoDb mongoDb

    void setupSpec() {
        fixWireMock()
    }

    void setup() {
        mongoDb.clear()
    }

    private static void fixWireMock() {
        System.setProperty('http.keepAlive', 'false')
        System.setProperty('http.maxConnections', '1')
    }
}
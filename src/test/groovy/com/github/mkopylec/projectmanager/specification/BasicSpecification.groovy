package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.api.ProjectManager
import com.github.mkopylec.projectmanager.utils.MongoDb
import com.github.mkopylec.projectmanager.utils.ProjectManagerHttpClient
import com.github.mkopylec.projectmanager.utils.ReportingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.web.client.RestOperations
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
abstract class BasicSpecification extends Specification {

    @Autowired
    protected ProjectManagerHttpClient http
    @Autowired
    protected ProjectManager projectManager
    @Autowired
    protected MongoDb mongoDb
    @Autowired
    protected ReportingService reportingService

    @MockBean
    private RestOperations restOperations
    @MockBean
    private MongoOperations mongo
}
package com.github.mkopylec.projectmanager.project

import com.github.mkopylec.projectmanager.common.Specification
import com.github.mkopylec.projectmanager.project.utils.api.ProjectHttpClient
import com.github.mkopylec.projectmanager.project.utils.event.ProjectPublishedEvents
import org.springframework.beans.factory.annotation.Autowired

abstract class ProjectSpecification extends Specification {

    @Autowired
    protected ProjectHttpClient projects
    @Autowired
    protected ProjectPublishedEvents publishedEvents

    @Override
    void cleanup() {
        publishedEvents.clear()
    }
}

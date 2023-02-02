package com.github.mkopylec.projectmanager.project.utils.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mkopylec.projectmanager.common.utils.event.PublishedEvents
import org.springframework.stereotype.Component

@Component
class ProjectPublishedEvents extends PublishedEvents {

    private ProjectPublishedEvents(ObjectMapper jsonMapper) {
        super([ProjectTeamAssigned, ProjectEnded], jsonMapper)
    }

    ProjectTeamAssigned getProjectTeamAssigned() {
        get(ProjectTeamAssigned)
    }

    ProjectEnded getProjectEnded() {
        get(ProjectEnded)
    }
}

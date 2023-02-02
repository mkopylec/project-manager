package com.github.mkopylec.projectmanager.project.outbound.local;

import com.github.mkopylec.projectmanager.common.outbound.local.JsonEventPayload;
import com.github.mkopylec.projectmanager.project.core.ProjectEnded;

import java.util.UUID;

class ProjectEndedPayload extends JsonEventPayload<ProjectEnded> {

    private final UUID projectId;

    ProjectEndedPayload(ProjectEnded event) {
        super(event);
        projectId = event.getProjectId().getValue();
    }

    UUID getProjectId() {
        return projectId;
    }
}

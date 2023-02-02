package com.github.mkopylec.projectmanager.project.outbound.local;

import com.github.mkopylec.projectmanager.common.outbound.local.JsonEventPayload;
import com.github.mkopylec.projectmanager.project.core.ProjectTeamAssigned;

import java.util.UUID;

class ProjectTeamAssignedPayload extends JsonEventPayload<ProjectTeamAssigned> {

    private final UUID projectId;
    private final String assignedTeam;

    ProjectTeamAssignedPayload(ProjectTeamAssigned event) {
        super(event);
        projectId = event.getProjectId().getValue();
        assignedTeam = event.getAssignedTeam().getValue();
    }

    UUID getProjectId() {
        return projectId;
    }

    String getAssignedTeam() {
        return assignedTeam;
    }
}

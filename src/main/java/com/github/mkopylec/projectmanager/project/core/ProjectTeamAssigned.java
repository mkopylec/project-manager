package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Event;

import java.time.Clock;

public class ProjectTeamAssigned extends Event {

    private final ProjectId projectId;
    private final TeamAssignedToProject assignedTeam;

    ProjectTeamAssigned(ProjectId projectId, TeamAssignedToProject assignedTeam, Clock clock) {
        super(clock);
        this.projectId = projectId;
        this.assignedTeam = assignedTeam;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public TeamAssignedToProject getAssignedTeam() {
        return assignedTeam;
    }
}

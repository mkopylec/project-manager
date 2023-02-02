package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Event;

import java.time.Clock;

public class ProjectEnded extends Event {

    private final ProjectId projectId;

    ProjectEnded(ProjectId projectId, Clock clock) {
        super(clock);
        this.projectId = projectId;
    }

    public ProjectId getProjectId() {
        return projectId;
    }
}

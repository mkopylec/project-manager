package com.github.mkopylec.projectmanager.project.outbound.local;

import com.github.mkopylec.projectmanager.common.outbound.local.EventConverter;
import com.github.mkopylec.projectmanager.common.outbound.local.JsonEventPayload;
import com.github.mkopylec.projectmanager.project.core.ProjectTeamAssigned;
import org.springframework.stereotype.Component;

@Component
class ProjectTeamAssignedConverter extends EventConverter<ProjectTeamAssigned> {

    private ProjectTeamAssignedConverter() {
        super(ProjectTeamAssigned.class);
    }

    @Override
    protected JsonEventPayload<ProjectTeamAssigned> convert(ProjectTeamAssigned event) {
        return new ProjectTeamAssignedPayload(event);
    }
}

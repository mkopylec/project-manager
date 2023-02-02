package com.github.mkopylec.projectmanager.project.outbound.local;

import com.github.mkopylec.projectmanager.common.outbound.local.EventConverter;
import com.github.mkopylec.projectmanager.common.outbound.local.JsonEventPayload;
import com.github.mkopylec.projectmanager.project.core.ProjectEnded;
import org.springframework.stereotype.Component;

@Component
class ProjectEndedConverter extends EventConverter<ProjectEnded> {

    private ProjectEndedConverter() {
        super(ProjectEnded.class);
    }

    @Override
    protected JsonEventPayload<ProjectEnded> convert(ProjectEnded event) {
        return new ProjectEndedPayload(event);
    }
}

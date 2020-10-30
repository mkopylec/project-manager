package com.github.mkopylec.projectmanager.core.project;

import java.util.UUID;

public class EndedProject {

    private UUID projectIdentifier;

    EndedProject(UUID projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    UUID getProjectIdentifier() {
        return projectIdentifier;
    }
}

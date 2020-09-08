package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.Event;

public class EndedProject implements Event {

    private String projectIdentifier;

    EndedProject(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }
}

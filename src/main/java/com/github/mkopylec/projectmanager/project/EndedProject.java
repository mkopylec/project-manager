package com.github.mkopylec.projectmanager.project;

public class EndedProject {

    private String projectIdentifier;

    EndedProject(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }
}

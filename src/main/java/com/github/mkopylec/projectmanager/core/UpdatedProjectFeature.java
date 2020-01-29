package com.github.mkopylec.projectmanager.core;

public class UpdatedProjectFeature {

    private String name;
    private Status status;
    private Requirement requirement;

    public UpdatedProjectFeature(String name, Status status, Requirement requirement) {
        this.name = name;
        this.status = status;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    private UpdatedProjectFeature() {
    }
}

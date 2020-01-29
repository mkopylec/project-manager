package com.github.mkopylec.projectmanager.core;

public class ExistingProjectFeature {

    private String name;
    private Status status;
    private Requirement requirement;

    ExistingProjectFeature() {
    }

    public String getName() {
        return name;
    }

    ExistingProjectFeature setName(String name) {
        this.name = name;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    ExistingProjectFeature setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    ExistingProjectFeature setRequirement(Requirement requirement) {
        this.requirement = requirement;
        return this;
    }
}

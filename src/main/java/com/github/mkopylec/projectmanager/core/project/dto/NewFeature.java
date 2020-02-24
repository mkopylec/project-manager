package com.github.mkopylec.projectmanager.core.project.dto;

public class NewFeature {

    private String name;
    private Requirement requirement;

    public NewFeature(String name, Requirement requirement) {
        this.name = name;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    private NewFeature() {
    }
}

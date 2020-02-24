package com.github.mkopylec.projectmanager.core.project.dto;

public class ExistingProjectDraft {

    private String identifier;
    private String name;

    public ExistingProjectDraft(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    private ExistingProjectDraft() {
    }
}

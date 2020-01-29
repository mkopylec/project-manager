package com.github.mkopylec.projectmanager.core;

public class ExistingProjectDraft {

    private String identifier;
    private String name;

    ExistingProjectDraft() {
    }

    public String getIdentifier() {
        return identifier;
    }

    ExistingProjectDraft setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public String getName() {
        return name;
    }

    ExistingProjectDraft setName(String name) {
        this.name = name;
        return this;
    }
}

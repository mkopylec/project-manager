package com.github.mkopylec.projectmanager.api.dto;

import java.util.UUID;

public class ExistingProjectDraft {

    private UUID identifier;
    private String name;

    public UUID getIdentifier() {
        return identifier;
    }

    public ExistingProjectDraft setIdentifier(UUID identifier) {
        this.identifier = identifier;
        return this;
    }

    public String getName() {
        return name;
    }

    public ExistingProjectDraft setName(String name) {
        this.name = name;
        return this;
    }
}

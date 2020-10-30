package com.github.mkopylec.projectmanager.api.dto;

import javax.validation.constraints.NotBlank;

public class NewProjectDraft {

    @NotBlank(message = "EMPTY_NEW_PROJECT_DRAFT_NAME")
    private String name;

    public NewProjectDraft(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private NewProjectDraft() {
    }
}

package com.github.mkopylec.projectmanager.core;

public class NewProjectDraft {

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
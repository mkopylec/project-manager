package com.github.mkopylec.projectmanager.core.team.dto;

public class NewTeam {

    private String name;

    public NewTeam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private NewTeam() {
    }
}

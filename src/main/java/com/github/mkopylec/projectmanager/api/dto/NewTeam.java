package com.github.mkopylec.projectmanager.api.dto;

import javax.validation.constraints.NotBlank;

public class NewTeam {

    @NotBlank(message = "EMPTY_NEW_TEAM_NAME")
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

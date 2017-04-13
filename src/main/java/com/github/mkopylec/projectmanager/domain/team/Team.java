package com.github.mkopylec.projectmanager.domain.team;

import org.springframework.data.annotation.Id;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Team {

    @Id
    private String name;
    private int currentlyImplementedProjects;

    public Team(String name) {
        validateName(name, "Error creating team");
        this.name = name;
        currentlyImplementedProjects = 0;
    }

    public String getName() {
        return name;
    }

    private void validateName(String name, String message) {
        when(isBlank(name))
                .thenInvalidEntity(EMPTY_TEAM_NAME, message);
    }

    private Team() {
    }
}

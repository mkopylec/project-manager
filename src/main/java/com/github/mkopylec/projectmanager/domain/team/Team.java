package com.github.mkopylec.projectmanager.domain.team;

import org.springframework.data.annotation.Id;

public class Team {

    @Id
    private String name;
    private int currentlyImplementedProjects;

    Team(String name, int currentlyImplementedProjects) {
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
    }
}

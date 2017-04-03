package com.github.mkopylec.projectmanager.domain.team;

public class Team {

    private String name;
    private int currentlyImplementedProjects;

    Team(String name, int currentlyImplementedProjects) {
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
    }
}

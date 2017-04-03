package com.github.mkopylec.projectmanager.domain.team;

import org.springframework.data.annotation.Id;

import static com.github.mkopylec.projectmanager.domain.exceptions.DomainException.when;

public class Team {

    @Id
    private String name;
    private int currentlyImplementedProjects;

    Team(String name, int currentlyImplementedProjects) {
        when(st)
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
    }
}

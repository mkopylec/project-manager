package com.github.mkopylec.projectmanager.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "teams")
class TeamDocument {

    @Id
    private String name;
    private int currentlyImplementedProjects;
    private List<EmployeeDocument> members;

    String getName() {
        return name;
    }

    TeamDocument setName(String name) {
        this.name = name;
        return this;
    }

    int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    TeamDocument setCurrentlyImplementedProjects(int currentlyImplementedProjects) {
        this.currentlyImplementedProjects = currentlyImplementedProjects;
        return this;
    }

    List<EmployeeDocument> getMembers() {
        return members;
    }

    TeamDocument setMembers(List<EmployeeDocument> members) {
        this.members = members;
        return this;
    }
}

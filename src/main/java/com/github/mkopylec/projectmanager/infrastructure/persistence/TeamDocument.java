package com.github.mkopylec.projectmanager.infrastructure.persistence;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.github.mkopylec.projectmanager.infrastructure.persistence.TeamDocument.TEAMS_COLLECTION;

@Document(collection = TEAMS_COLLECTION)
class TeamDocument {

    static final String TEAMS_COLLECTION = "teams";

    @Id
    private String name;
    private int currentlyImplementedProjects;
    private List<EmployeeDocument> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    public void setCurrentlyImplementedProjects(int currentlyImplementedProjects) {
        this.currentlyImplementedProjects = currentlyImplementedProjects;
    }

    public List<EmployeeDocument> getMembers() {
        return members;
    }

    public void setMembers(List<EmployeeDocument> members) {
        this.members = members;
    }
}

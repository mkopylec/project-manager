package com.github.mkopylec.projectmanager.api.dto;

import java.util.List;

public class ExistingTeam {

    private String name;
    private int currentlyImplementedProjects;
    private boolean busy;
    private List<ExistingTeamMember> members;

    public String getName() {
        return name;
    }

    public ExistingTeam setName(String name) {
        this.name = name;
        return this;
    }

    public int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    public ExistingTeam setCurrentlyImplementedProjects(int currentlyImplementedProjects) {
        this.currentlyImplementedProjects = currentlyImplementedProjects;
        return this;
    }

    public boolean isBusy() {
        return busy;
    }

    public ExistingTeam setBusy(boolean busy) {
        this.busy = busy;
        return this;
    }

    public List<ExistingTeamMember> getMembers() {
        return members;
    }

    public ExistingTeam setMembers(List<ExistingTeamMember> members) {
        this.members = members;
        return this;
    }
}

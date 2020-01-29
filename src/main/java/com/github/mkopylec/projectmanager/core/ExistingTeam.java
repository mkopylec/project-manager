package com.github.mkopylec.projectmanager.core;

import java.util.List;

public class ExistingTeam {

    private String name;
    private int currentlyImplementedProjects;
    private boolean busy;
    private List<ExistingTeamMember> members;

    ExistingTeam() {
    }

    public String getName() {
        return name;
    }

    ExistingTeam setName(String name) {
        this.name = name;
        return this;
    }

    public int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    ExistingTeam setCurrentlyImplementedProjects(int currentlyImplementedProjects) {
        this.currentlyImplementedProjects = currentlyImplementedProjects;
        return this;
    }

    public boolean isBusy() {
        return busy;
    }

    ExistingTeam setBusy(boolean busy) {
        this.busy = busy;
        return this;
    }

    public List<ExistingTeamMember> getMembers() {
        return members;
    }

    ExistingTeam setMembers(List<ExistingTeamMember> members) {
        this.members = members;
        return this;
    }
}

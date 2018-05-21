package com.github.mkopylec.projectmanager.core.team.dto;

import java.util.List;

public class ExistingTeam {

    private String name;
    private int currentlyImplementedProjects;
    private boolean busy;
    private List<TeamMember> members;

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

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }
}

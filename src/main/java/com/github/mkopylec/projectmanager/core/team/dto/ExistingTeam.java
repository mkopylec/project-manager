package com.github.mkopylec.projectmanager.core.team.dto;

import java.util.List;

public class ExistingTeam {

    private String name;
    private int currentlyImplementedProjects;
    private boolean busy;
    private List<ExistingTeamMember> members;

    public ExistingTeam(String name, int currentlyImplementedProjects, boolean busy, List<ExistingTeamMember> members) {
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
        this.busy = busy;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    public boolean isBusy() {
        return busy;
    }

    public List<ExistingTeamMember> getMembers() {
        return members;
    }

    private ExistingTeam() {
    }
}

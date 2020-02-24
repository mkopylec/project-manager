package com.github.mkopylec.projectmanager.core.team.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("currentlyImplementedProjects", currentlyImplementedProjects)
                .append("busy", busy)
                .append("members", members)
                .toString();
    }

    private ExistingTeam() {
    }
}

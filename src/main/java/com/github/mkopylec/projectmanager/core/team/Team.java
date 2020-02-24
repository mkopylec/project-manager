package com.github.mkopylec.projectmanager.core.team;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.allEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.team.TeamRequirementsValidator.teamRequirements;
import static java.util.Collections.unmodifiableList;

public class Team {

    private static final int BUSY_TEAM_THRESHOLD = 3;

    private String name;
    private int currentlyImplementedProjects;
    private List<Employee> members;

    static Team team(String name) {
        return new Team(name, 0, null);
    }

    private Team(String name, int currentlyImplementedProjects, List<Employee> members) {
        teamRequirements()
                .requireName(name)
                .requireValidCurrentlyImplementedProjects(currentlyImplementedProjects)
                .requireValidMembers(members)
                .validate();
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
        this.members = neverNull(members);
    }

    String getName() {
        return name;
    }

    int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    List<Employee> getMembers() {
        return unmodifiableList(members);
    }

    boolean isBusy() {
        return currentlyImplementedProjects > BUSY_TEAM_THRESHOLD;
    }

    void addMember(Employee member) {
        teamRequirements()
                .requireValidMember(member)
                .validate();
        members.add(member);
    }

    public static class TeamPersistenceHelper {

        public Team createTeam(String name, int currentlyImplementedProjects, List<Employee> members) {
            try {
                return allEmpty(name, currentlyImplementedProjects, members) ? null : new Team(name, currentlyImplementedProjects, members);
            } catch (Exception e) {
                throw new IllegalStateException("Error creating team from persistent state", e);
            }
        }

        public String getName(Team team) {
            return team.name;
        }

        public int getCurrentlyImplementedProjects(Team team) {
            return team.currentlyImplementedProjects;
        }

        public List<Employee> getMembers(Team team) {
            return unmodifiableList(team.members);
        }
    }
}

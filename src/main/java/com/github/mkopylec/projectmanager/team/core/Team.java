package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.Aggregate;
import com.github.mkopylec.projectmanager.common.core.AggregateStateVersion;

import java.util.List;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.support.ListUtils.cleanToModifiable;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public class Team extends Aggregate<TeamName> {

    private TeamName name;
    private BusyTeamThreshold busyThreshold;
    private TeamCurrentlyImplementedProjects currentlyImplementedProjects;
    private List<Member> members;

    private Team(TeamName name, AggregateStateVersion stateVersion, BusyTeamThreshold busyThreshold, TeamCurrentlyImplementedProjects currentlyImplementedProjects, List<Member> members) {
        super(name, stateVersion);
        setName(name);
        setBusyThreshold(busyThreshold);
        setCurrentlyImplementedProjects(currentlyImplementedProjects);
        setMembers(members);
    }

    Team(TeamName name, BusyTeamThreshold busyThreshold) {
        this(name, new AggregateStateVersion(), busyThreshold, new TeamCurrentlyImplementedProjects(), emptyList());
    }

    public static Team fromPersistentState(TeamName name, AggregateStateVersion stateVersion, BusyTeamThreshold busyThreshold, TeamCurrentlyImplementedProjects currentlyImplementedProjects, List<Member> members) {
        return requireNoBusinessRuleViolation(() -> new Team(name, stateVersion, busyThreshold, currentlyImplementedProjects, members));
    }

    public TeamName getName() {
        return name;
    }

    public BusyTeamThreshold getBusyThreshold() {
        return busyThreshold;
    }

    public TeamCurrentlyImplementedProjects getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    public List<Member> getMembers() {
        return members;
    }

    void addCurrentlyImplementedProject() {
        currentlyImplementedProjects = currentlyImplementedProjects.addProject();
    }

    void removeCurrentlyImplementedProject() {
        currentlyImplementedProjects = currentlyImplementedProjects.removeProject();
    }

    void addMember(Member member) { // TODO Scheduler that updates members using some HTTP outbound adapter
        if (member != null) {
            members.add(member);
        }
    }

    boolean isBusy() {
        return currentlyImplementedProjects.exceeds(busyThreshold);
    }

    private void setName(TeamName name) {
        this.name = requireNonNull(name, "No team name");
    }

    private void setBusyThreshold(BusyTeamThreshold busyThreshold) {
        this.busyThreshold = requireNonNull(busyThreshold, "No busy team threshold");
    }

    private void setCurrentlyImplementedProjects(TeamCurrentlyImplementedProjects currentlyImplementedProjects) {
        this.currentlyImplementedProjects = requireNonNull(currentlyImplementedProjects, "No team currently implemented projects");
    }

    private void setMembers(List<Member> members) {
        this.members = cleanToModifiable(members);
    }
}

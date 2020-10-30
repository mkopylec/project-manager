package com.github.mkopylec.projectmanager.core.team;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.EMPTY_TEAM_MEMBER;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isNotEmpty;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.noneEmpty;
import static java.util.Collections.unmodifiableList;

@Document(collection = "teams")
public class Team {

    private static final int BUSY_TEAM_THRESHOLD = 3;

    @Id
    private String name;
    private int currentlyImplementedProjects;
    private List<Member> members;

    Team(String name) {
        this(name, 0, null);
    }

    @PersistenceConstructor
    private Team(String name, int currentlyImplementedProjects, List<Member> members) {
        require(isNotEmpty(name), EMPTY_TEAM_NAME);
        require(currentlyImplementedProjects >= 0, INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM);
        require(noneEmpty(members), EMPTY_TEAM_MEMBER);
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
        this.members = neverNull(members);
    }

    public String getName() {
        return name;
    }

    public int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    public List<Member> getMembers() {
        return unmodifiableList(members);
    }

    public boolean isBusy() {
        return currentlyImplementedProjects > BUSY_TEAM_THRESHOLD;
    }

    void addCurrentlyImplementedProject() {
        currentlyImplementedProjects++;
    }

    void removeCurrentlyImplementedProject() {
        currentlyImplementedProjects--;
    }

    void addMember(Member member) {
        require(isNotEmpty(member), EMPTY_TEAM_MEMBER);
        members.add(member);
    }
}

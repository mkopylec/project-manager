package com.github.mkopylec.projectmanager.core.team;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_MEMBER;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_MEMBER_FIRST_NAME;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_MEMBER_LAST_NAME;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.INVALID_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.core.team.PreCondition.when;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Team {

    private static final int BUSY_TEAM_THRESHOLD = 3;

    @Id
    private String name;
    private int currentlyImplementedProjects;
    private List<Employee> members;

    Team(String name) {
        validateName(name, "Error creating team");
        this.name = name;
        currentlyImplementedProjects = 0;
        members = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    void addCurrentlyImplementedProject() {
        currentlyImplementedProjects++;
    }

    List<Employee> getMembers() {
        return unmodifiableList(members);
    }

    void addMember(Employee member) {
        validateMember(member, "Error adding member to '" + name + "' team");
        members.add(member);
    }

    boolean isBusy() {
        return currentlyImplementedProjects > BUSY_TEAM_THRESHOLD;
    }

    private void validateName(String name, String message) {
        when(isBlank(name))
                .thenInvalidTeam(EMPTY_TEAM_NAME, message);
    }

    private void validateMember(Employee member, String message) {
        when(member == null)
                .thenInvalidTeam(EMPTY_MEMBER, message);
        when(member.hasNoFirstName())
                .thenInvalidTeam(EMPTY_MEMBER_FIRST_NAME, message);
        when(member.hasNoLastName())
                .thenInvalidTeam(EMPTY_MEMBER_LAST_NAME, message);
        when(member.hasNoJobPosition())
                .thenInvalidTeam(EMPTY_MEMBER_JOB_POSITION, message);
        when(member.hasInvalidJobPosition())
                .thenInvalidTeam(INVALID_MEMBER_JOB_POSITION, message);
    }

    private Team() {
    }
}

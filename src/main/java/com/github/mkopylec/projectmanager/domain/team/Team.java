package com.github.mkopylec.projectmanager.domain.team;

import com.github.mkopylec.projectmanager.domain.values.Employee;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER_FIRST_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER_LAST_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Team {

    private static final int BUSY_TEAM_THRESHOLD = 3;

    @Id
    private String name;
    private int currentlyImplementedProjects;
    private List<Employee> members;

    public Team(String name) {
        validateName(name, "Error creating team");
        this.name = name;
        currentlyImplementedProjects = 0;
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCurrentlyImplementedProjects() {
        return currentlyImplementedProjects;
    }

    public void addCurrentlyImplementedProject() {
        currentlyImplementedProjects++;
    }

    public List<Employee> getMembers() {
        return unmodifiableList(members);
    }

    public void addMember(Employee member) {
        validateMember(member, "Error adding member to '" + name + "' team");
        members.add(member);
    }

    public boolean isBusy() {
        return currentlyImplementedProjects > BUSY_TEAM_THRESHOLD;
    }

    private void validateName(String name, String message) {
        when(isBlank(name))
                .thenInvalidEntity(EMPTY_TEAM_NAME, message);
    }

    private void validateMember(Employee member, String message) {
        when(member == null)
                .thenInvalidEntity(EMPTY_MEMBER, message);
        when(member.hasNoFirstName())
                .thenInvalidEntity(EMPTY_MEMBER_FIRST_NAME, message);
        when(member.hasNoLastName())
                .thenInvalidEntity(EMPTY_MEMBER_LAST_NAME, message);
        when(member.hasNoJobPosition())
                .thenInvalidEntity(EMPTY_MEMBER_JOB_POSITION, message);
    }

    private Team() {
    }
}

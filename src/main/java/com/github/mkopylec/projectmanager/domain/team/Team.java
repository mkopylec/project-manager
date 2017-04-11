package com.github.mkopylec.projectmanager.domain.team;

import java.util.ArrayList;
import java.util.List;

import com.github.mkopylec.projectmanager.domain.values.Employee;

import org.springframework.data.annotation.Id;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER_FIRST_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_MEMBER_LAST_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.INVALID_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Team {

    @Id
    private String name;
    private int currentlyImplementedProjects;
    private List<Employee> members;

    public Team(String name) {
        when(isBlank(name))
                .thenInvalidEntity(EMPTY_TEAM_NAME, "Error creating team");
        this.name = name;
        currentlyImplementedProjects = 0;
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMember(Employee member) {
        validateMember(member, "Error adding member to '" + name + "' team");
        members.add(member);
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
        when(member.hasInvalidJobPosition())
                .thenInvalidEntity(INVALID_MEMBER_JOB_POSITION, message);
    }

    private Team() {
    }
}

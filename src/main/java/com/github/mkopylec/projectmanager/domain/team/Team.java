package com.github.mkopylec.projectmanager.domain.team;

import java.util.List;

import com.github.mkopylec.projectmanager.domain.values.Employee;

import org.springframework.data.annotation.Id;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Team {

    @Id
    private String name;
    private int currentlyImplementedProjects;
    private List<Employee> members;

    Team(String name, int currentlyImplementedProjects, List<Employee> members) {
        when(isBlank(name)).thenInvalidEntity(EMPTY_TEAM_NAME, "Error creating team");
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
        this.members = emptyIfNull(members);
    }

    public String getName() {
        return name;
    }
}

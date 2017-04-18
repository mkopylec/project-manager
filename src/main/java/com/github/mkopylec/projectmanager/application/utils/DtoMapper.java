package com.github.mkopylec.projectmanager.application.utils;

import com.github.mkopylec.projectmanager.application.dto.TeamMember;
import com.github.mkopylec.projectmanager.domain.values.Employee;

public class DtoMapper {

    public static Employee mapToEmployee(TeamMember teamMember) {
        return new Employee(teamMember.getFirstName(), teamMember.getLastName(), teamMember.getJobPosition());
    }

    private DtoMapper() {
    }
}

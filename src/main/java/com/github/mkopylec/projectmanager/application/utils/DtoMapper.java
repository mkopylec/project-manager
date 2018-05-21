package com.github.mkopylec.projectmanager.application.utils;

import com.github.mkopylec.projectmanager.application.dto.TeamMember;
import com.github.mkopylec.projectmanager.domain.values.Employee;
import com.github.mkopylec.projectmanager.domain.values.JobPosition;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class DtoMapper {

    public static Employee mapToEmployee(TeamMember teamMember) {
        JobPosition jobPosition = mapToJobPosition(teamMember.getJobPosition());
        return new Employee(teamMember.getFirstName(), teamMember.getLastName(), jobPosition);
    }

    private static JobPosition mapToJobPosition(String jobPosition) {
        if (isBlank(jobPosition)) {
            return null;
        }
        try {
            return JobPosition.valueOf(jobPosition);
        } catch (IllegalArgumentException e) {
            return JobPosition.INVALID;
        }
    }

    private DtoMapper() {
    }
}

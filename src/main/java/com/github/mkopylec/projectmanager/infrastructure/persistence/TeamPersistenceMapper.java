package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.team.Employee;
import com.github.mkopylec.projectmanager.core.team.Employee.EmployeePersistenceHelper;
import com.github.mkopylec.projectmanager.core.team.JobPosition;
import com.github.mkopylec.projectmanager.core.team.Team;
import com.github.mkopylec.projectmanager.core.team.Team.TeamPersistenceHelper;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static com.github.mkopylec.projectmanager.core.common.Utilities.toEnum;

class TeamPersistenceMapper {

    private TeamPersistenceHelper teamHelper = new TeamPersistenceHelper();
    private EmployeePersistenceHelper employeeHelper = new EmployeePersistenceHelper();

    TeamDocument map(Team team) {
        return new TeamDocument()
                .setName(teamHelper.getName(team))
                .setCurrentlyImplementedProjects(teamHelper.getCurrentlyImplementedProjects(team))
                .setMembers(mapElements(teamHelper.getMembers(team), this::map));
    }

    Team map(TeamDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        var employees = mapElements(document.getMembers(), this::map);
        return teamHelper.createTeam(document.getName(), document.getCurrentlyImplementedProjects(), employees);
    }

    private EmployeeDocument map(Employee employee) {
        return new EmployeeDocument()
                .setFirstName(employeeHelper.getFirstName(employee))
                .setLastName(employeeHelper.getLastName(employee))
                .setJobPosition(employeeHelper.getJobPosition(employee).name());
    }

    private Employee map(EmployeeDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        var jobPosition = toEnum(document.getJobPosition(), JobPosition.class);
        return employeeHelper.createEmployee(document.getFirstName(), document.getLastName(), jobPosition);
    }
}

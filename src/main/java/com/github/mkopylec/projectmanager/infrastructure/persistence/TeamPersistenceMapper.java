package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.team.Employee;
import com.github.mkopylec.projectmanager.core.team.Employee.EmployeePersistenceFactory;
import com.github.mkopylec.projectmanager.core.team.JobPosition;
import com.github.mkopylec.projectmanager.core.team.Team;
import com.github.mkopylec.projectmanager.core.team.Team.TeamPersistenceFactory;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static com.github.mkopylec.projectmanager.core.common.Utilities.toEnum;

class TeamPersistenceMapper {

    private TeamPersistenceFactory teamFactory = new TeamPersistenceFactory();
    private EmployeePersistenceFactory employeeFactory = new EmployeePersistenceFactory();

    TeamDocument map(Team team) {
        return new TeamDocument()
                .setName(team.getName())
                .setCurrentlyImplementedProjects(team.getCurrentlyImplementedProjects())
                .setMembers(mapElements(team.getMembers(), this::map));
    }

    Team map(TeamDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        List<Employee> employees = mapElements(document.getMembers(), this::map);
        return teamFactory.createTeam(document.getName(), document.getCurrentlyImplementedProjects(), employees);
    }

    private EmployeeDocument map(Employee employee) {
        return new EmployeeDocument()
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setJobPosition(employee.getJobPosition().name());
    }

    private Employee map(EmployeeDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        JobPosition jobPosition = toEnum(document.getJobPosition(), JobPosition.class);
        return employeeFactory.createEmployee(document.getFirstName(), document.getLastName(), jobPosition);
    }
}

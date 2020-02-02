package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.NewTeam;
import com.github.mkopylec.projectmanager.core.NewTeamMember;

import static com.github.mkopylec.projectmanager.core.common.Utilities.convertEnum;
import static com.github.mkopylec.projectmanager.core.team.Employee.employee;
import static com.github.mkopylec.projectmanager.core.team.Team.team;

class TeamFactory {

    Team createTeam(NewTeam newTeam) {
        return team(newTeam.getName());
    }

    Employee createMember(NewTeamMember newTeamMember) {
        var jobPosition = convertEnum(newTeamMember.getJobPosition(), JobPosition.class);
        return employee(newTeamMember.getFirstName(), newTeamMember.getLastName(), jobPosition);
    }
}

package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.api.dto.NewTeam;
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember;
import org.springframework.stereotype.Component;

@Component
class TeamFactory {

    Team createTeam(NewTeam newTeam) {
        return new Team(newTeam.getName());
    }

    Member createMember(NewTeamMember newTeamMember) {
        var jobPosition = JobPosition.from(newTeamMember.getJobPosition());
        return new Member(newTeamMember.getFirstName(), newTeamMember.getLastName(), jobPosition);
    }
}

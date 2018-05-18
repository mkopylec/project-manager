package com.github.mkopylec.projectmanager.team;

import com.github.mkopylec.projectmanager.application.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.application.dto.NewTeam;
import com.github.mkopylec.projectmanager.application.dto.TeamMember;

import java.util.List;

public interface TeamApi {

    void createTeam(NewTeam newTeam);

    void addMemberToTeam(String teamName, TeamMember teamMember);

    List<ExistingTeam> getTeams();
}

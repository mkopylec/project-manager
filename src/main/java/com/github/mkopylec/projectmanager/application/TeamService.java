package com.github.mkopylec.projectmanager.application;

import com.github.mkopylec.projectmanager.application.dto.NewTeam;
import com.github.mkopylec.projectmanager.application.dto.NewTeamMember;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamFactory;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;

import org.springframework.stereotype.Service;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.TEAM_ALREADY_EXISTS;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;

@Service
public class TeamService {

    private final TeamFactory teamFactory;
    private final TeamRepository teamRepository;

    public TeamService(TeamFactory teamFactory, TeamRepository teamRepository) {
        this.teamFactory = teamFactory;
        this.teamRepository = teamRepository;
    }

    public void createTeam(NewTeam newTeam) {
        Team team = teamFactory.createTeam(newTeam.getName());
        when(teamRepository.existsByName(team.getName()))
                .thenEntityAlreadyExists(TEAM_ALREADY_EXISTS, "Error creating team named '" + team.getName() + "'");
        teamRepository.save(team);
    }

    public void createTeam(String teamName, NewTeamMember newTeamMember) {

    }
}

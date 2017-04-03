package com.github.mkopylec.projectmanager.application;

import com.github.mkopylec.projectmanager.api.TeamsEndpoint;
import com.github.mkopylec.projectmanager.api.dto.NewTeam;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamFactory;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TeamsService implements TeamsEndpoint {

    private final TeamFactory teamFactory;
    private final TeamRepository teamRepository;

    TeamsService(TeamFactory teamFactory, TeamRepository teamRepository) {
        this.teamFactory = teamFactory;
        this.teamRepository = teamRepository;
    }

    @Override
    public void createTeam(@RequestBody NewTeam newTeam) {
        Team team = teamFactory.createTeam(newTeam.getName());
        teamRepository.save(team);
    }
}

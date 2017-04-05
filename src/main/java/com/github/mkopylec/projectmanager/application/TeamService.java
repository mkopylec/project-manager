package com.github.mkopylec.projectmanager.application;

import com.github.mkopylec.projectmanager.application.dto.NewTeam;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamFactory;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TeamService {

    private final TeamFactory teamFactory;
    private final TeamRepository teamRepository;

    public TeamService(TeamFactory teamFactory, TeamRepository teamRepository) {
        this.teamFactory = teamFactory;
        this.teamRepository = teamRepository;
    }

    public void createTeam(@RequestBody NewTeam newTeam) {
        Team team = teamFactory.createTeam(newTeam.getName());
        teamRepository.save(team);
    }
}

package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam;
import com.github.mkopylec.projectmanager.core.team.dto.TeamMember;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.team.DtoMapper.mapToEmployee;
import static com.github.mkopylec.projectmanager.core.team.DtoMapper.mapToExistingTeams;
import static com.github.mkopylec.projectmanager.core.team.PreCondition.when;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void createTeam(NewTeam newTeam) {
        Team team = new Team(newTeam.getName());
        when(teamRepository.existsByName(team.getName()))
                .thenTeamAlreadyExists("Error creating team named '" + team.getName() + "'");
        teamRepository.save(team);
    }

    public void addMemberToTeam(String teamName, TeamMember teamMember) {
        Team team = teamRepository.findByName(teamName);
        when(team == null)
                .thenMissingTeam("Error adding member to '" + teamName + "' team");
        Employee member = mapToEmployee(teamMember);
        team.addMember(member);
        teamRepository.save(team);
    }

    public List<ExistingTeam> getTeams() {
        List<Team> teams = teamRepository.findAll();
        return mapToExistingTeams(teams);
    }

    public void addImplementedProjectToTeam(String teamName) {
        if (isBlank(teamName)) {
            return;
        }
        Team team = teamRepository.findByName(teamName);
        team.addCurrentlyImplementedProject();
        teamRepository.save(team);
    }
}

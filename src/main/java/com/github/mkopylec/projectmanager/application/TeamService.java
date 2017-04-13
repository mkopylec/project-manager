package com.github.mkopylec.projectmanager.application;

import java.util.List;

import com.github.mkopylec.projectmanager.application.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.application.dto.NewTeam;
import com.github.mkopylec.projectmanager.application.dto.TeamMember;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;
import com.github.mkopylec.projectmanager.domain.values.Employee;

import org.springframework.stereotype.Service;

import static com.github.mkopylec.projectmanager.application.utils.DtoMapper.mapToExistingTeams;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.NONEXISTENT_TEAM;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.TEAM_ALREADY_EXISTS;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void createTeam(NewTeam newTeam) {
        Team team = new Team(newTeam.getName());
        when(teamRepository.existsByName(team.getName()))
                .thenEntityAlreadyExists(TEAM_ALREADY_EXISTS, "Error creating team named '" + team.getName() + "'");
        teamRepository.save(team);
    }

    public void addMemberToTeam(String teamName, TeamMember teamMember) {
        Team team = teamRepository.findByName(teamName);
        when(team == null)
                .thenMissingEntity(NONEXISTENT_TEAM, "Error adding member to '" + teamName + "' team");
        Employee member = new Employee(teamMember.getFirstName(), teamMember.getLastName(), teamMember.getJobPosition());
        team.addMember(member);
        teamRepository.save(team);
    }

    public List<ExistingTeam> getTeams() {
        List<Team> teams = teamRepository.findAll();
        return mapToExistingTeams(teams);
    }
}

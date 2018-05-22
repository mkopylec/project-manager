package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.TeamAssignedToProject;
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam;
import com.github.mkopylec.projectmanager.core.team.dto.TeamMember;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

import static com.github.mkopylec.projectmanager.core.team.DtoMapper.mapToEmployee;
import static com.github.mkopylec.projectmanager.core.team.DtoMapper.mapToExistingTeams;
import static com.github.mkopylec.projectmanager.core.team.PreCondition.when;

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

    public void addImplementedProjectToTeam(TeamAssignedToProject teamAssignedToProject) {
        updateTeamImplementedProjects(teamAssignedToProject, team -> team.addCurrentlyImplementedProject());
    }

    public void removeImplementedProjectFromTeam(TeamAssignedToProject teamAssignedToProject) {
        updateTeamImplementedProjects(teamAssignedToProject, team -> team.removeCurrentlyImplementedProject());
    }

    private void updateTeamImplementedProjects(TeamAssignedToProject teamAssignedToProject, Consumer<Team> teamUpdater) {
        if (teamAssignedToProject == null) {
            return;
        }
        Team team = teamRepository.findByName(teamAssignedToProject.getName());
        teamUpdater.accept(team);
        teamRepository.save(team);
    }
}

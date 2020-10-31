package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.api.dto.NewTeam;
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember;
import com.github.mkopylec.projectmanager.core.project.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.api.exception.MissingEntityException.requireExisting;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.MISSING_TEAM;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.MISSING_TEAM_ASSIGNED_TO_PROJECT;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.TEAM_EXISTS;

@Service
public class TeamService {

    private TeamFactory factory;
    private TeamRepository repository;

    public TeamService(TeamFactory factory, TeamRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public void createTeam(NewTeam newTeam) {
        var existingTeam = repository.findByName(newTeam.getName());
        require(isEmpty(existingTeam), TEAM_EXISTS);
        var team = factory.createTeam(newTeam);
        repository.save(team);
    }

    public void addTeamMember(String teamName, NewTeamMember newTeamMember) {
        var team = repository.findByName(teamName);
        requireExisting(team, MISSING_TEAM);
        var member = factory.createMember(newTeamMember);
        team.addMember(member);
        repository.save(team);
    }

    public List<Team> getTeams() {
        return repository.findAll();
    }

    public void addTeamImplementedProject(Project project) {
        updateTeamAssignedToProject(project, Team::addCurrentlyImplementedProject);
    }

    public void removeTeamImplementedProject(Project project) {
        updateTeamAssignedToProject(project, Team::removeCurrentlyImplementedProject);
    }

    private void updateTeamAssignedToProject(Project project, Consumer<Team> update) {
        if (project.hasNoTeamAssigned()) {
            return;
        }
        var team = repository.findByName(project.getAssignedTeam());
        requireExisting(team, MISSING_TEAM_ASSIGNED_TO_PROJECT);
        update.accept(team);
        repository.save(team);
    }
}

package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.api.dto.NewTeam;
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember;
import com.github.mkopylec.projectmanager.core.project.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.api.exception.MissingEntityException.requireExisting;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.MISSING_TEAM;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.MISSING_TEAM_ASSIGNED_TO_PROJECT;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.TEAM_EXISTS;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isNotEmpty;

@Service
public class TeamService {

    private TeamFactory factory;
    private TeamRepository repository;

    public TeamService(TeamFactory factory, TeamRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public Team createTeam(NewTeam newTeam) {
        var existingTeam = repository.findByName(newTeam.getName());
        require(isEmpty(existingTeam), TEAM_EXISTS);
        return factory.createTeam(newTeam);
    }

    public Team getTeamWithAddedMember(String teamName, NewTeamMember newTeamMember) {
        var team = repository.findByName(teamName);
        requireExisting(team, MISSING_TEAM);
        var member = factory.createMember(newTeamMember);
        team.addMember(member);
        return team;
    }

    public List<Team> getTeams() {
        return repository.findAll();
    }

    public Team getTeamWithAddedImplementedProject(Project project) {
        return getUpdatedTeamAssignedToProject(project, Team::addCurrentlyImplementedProject);
    }

    public Team getTeamWithRemovedImplementedProject(Project project) {
        return getUpdatedTeamAssignedToProject(project, Team::removeCurrentlyImplementedProject);
    }

    public void saveTeam(Team team) {
        if (isNotEmpty(team)) {
            repository.save(team);
        }
    }

    private Team getUpdatedTeamAssignedToProject(Project project, Consumer<Team> update) {
        if (project.hasNoTeamAssigned()) {
            return null;
        }
        var team = repository.findByName(project.getAssignedTeam());
        requireExisting(team, MISSING_TEAM_ASSIGNED_TO_PROJECT);
        update.accept(team);
        return team;
    }
}

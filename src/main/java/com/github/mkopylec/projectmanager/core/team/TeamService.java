package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.NewTeam;
import com.github.mkopylec.projectmanager.core.NewTeamMember;
import com.github.mkopylec.projectmanager.core.project.Project;

import java.util.List;
import java.util.function.Consumer;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isNotEmpty;
import static com.github.mkopylec.projectmanager.core.team.TeamRequirementsValidator.requirements;

public class TeamService {

    private TeamFactory factory = new TeamFactory();
    private TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Team createTeam(NewTeam newTeam) {
        var existingTeam = repository.findByName(newTeam.getName());
        requirements()
                .requireNoTeam(existingTeam)
                .validate();
        return factory.createTeam(newTeam);
    }

    public Team getTeamWithAddedMember(String teamName, NewTeamMember newTeamMember) {
        var team = repository.findByName(teamName);
        requirements()
                .requireTeam(team)
                .validate();
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
        requirements()
                .requireTeamAssignedToProject(team)
                .validate();
        update.accept(team);
        return team;
    }
}

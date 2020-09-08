package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.NewTeam;
import com.github.mkopylec.projectmanager.core.NewTeamMember;
import com.github.mkopylec.projectmanager.core.project.Project;

import java.util.List;
import java.util.function.Consumer;

import static com.github.mkopylec.projectmanager.core.team.TeamRequirementsValidator.requirements;

public class TeamService {

    private TeamFactory factory = new TeamFactory();
    private TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public void createTeam(NewTeam newTeam) {
        var existingTeam = repository.findByName(newTeam.getName());
        requirements()
                .requireNoTeam(existingTeam)
                .validate();
        var team = factory.createTeam(newTeam);
        repository.save(team);
    }

    public void getTeamWithAddedMember(String teamName, NewTeamMember newTeamMember) {
        var team = repository.findByName(teamName);
        requirements()
                .requireTeam(team)
                .validate();
        var member = factory.createMember(newTeamMember);
        team.addMember(member);
        repository.save(team);
    }

    public List<Team> getTeams() {
        return repository.findAll();
    }

    public void addImplementedProjectToTeam(Project project) {
        updateTeamAssignedToProject(project, Team::addCurrentlyImplementedProject);
    }

    public void removeImplementedProjectFromTeam(Project project) {
        updateTeamAssignedToProject(project, Team::removeCurrentlyImplementedProject);
    }

    private void updateTeamAssignedToProject(Project project, Consumer<Team> update) {
        if (project.hasNoTeamAssigned()) {
            return;
        }
        var team = repository.findByName(project.getAssignedTeam());
        requirements()
                .requireTeamAssignedToProject(team)
                .validate();
        update.accept(team);
        repository.save(team);
    }
}

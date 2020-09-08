package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.EventPublisher;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.project.UniqueIdentifierGenerator;
import com.github.mkopylec.projectmanager.core.team.TeamRepository;
import com.github.mkopylec.projectmanager.core.team.TeamService;

import java.util.List;
import java.util.function.Supplier;

import static com.github.mkopylec.projectmanager.core.ProjectManagerException.projectManagerException;
import static com.github.mkopylec.projectmanager.core.common.Utilities.ifNotEmpty;
import static java.util.List.of;

/**
 * Primary port
 */
public class ProjectManager {

    private ProjectManagerService service;
    private final UnitOfWork unitOfWork;

    public ProjectManager(UniqueIdentifierGenerator identifierGenerator, ProjectRepository projectRepository, EventPublisher eventPublisher, TeamRepository teamRepository) {
        var projectService = new ProjectService(identifierGenerator, projectRepository, eventPublisher);
        var teamService = new TeamService(teamRepository);
        service = new ProjectManagerService(projectService, teamService);
        unitOfWork = new UnitOfWork(of(projectRepository, teamRepository, eventPublisher));
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        execute(() -> service.createProject(newProjectDraft), "Creating '" + ifNotEmpty(newProjectDraft, NewProjectDraft::getName) + "' project has failed");
    }

    public void createProject(NewProject newProject) {
        execute(() -> service.createProject(newProject), "Creating '" + ifNotEmpty(newProject, NewProject::getName) + "' project has failed");
    }

    public List<ExistingProjectDraft> getProjects() {
        return execute(() -> service.getProjects(), "Getting projects has failed");
    }

    public ExistingProject getProject(String projectIdentifier) {
        return execute(() -> service.getProject(projectIdentifier), "Getting '" + projectIdentifier + "' project has failed");
    }

    public void updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        execute(() -> service.updateProject(projectIdentifier, updatedProject), "Updating '" + projectIdentifier + "' project has failed");
    }

    public void startProject(String projectIdentifier) {
        execute(() -> service.startProject(projectIdentifier), "Starting '" + projectIdentifier + "' project has failed");
    }

    public void endProject(String projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        execute(() -> service.endProject(projectIdentifier, projectEndingCondition), "Ending '" + projectIdentifier + "' project has failed");
    }

    public void createTeam(NewTeam newTeam) {
        execute(() -> service.createTeam(newTeam), "Creating '" + ifNotEmpty(newTeam, NewTeam::getName) + "' team has failed");
    }

    public void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        execute(() -> service.addMemberToTeam(teamName, newTeamMember), "Adding member to '" + teamName + "' team has failed");
    }

    public List<ExistingTeam> getTeams() {
        return execute(() -> service.getTeams(), "Getting teams has failed");
    }

    private void execute(Runnable useCase, String errorMessage) {
        execute(() -> {
            useCase.run();
            return null;
        }, errorMessage);
    }

    private <T> T execute(Supplier<T> useCase, String errorMessage) {
        try (unitOfWork) {
            var result = useCase.get();
            unitOfWork.commit();
            return result;
        } catch (Exception e) {
            throw projectManagerException(e, errorMessage);
        }
    }
}

package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.EventPublisher;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.project.UniqueIdentifierGenerator;
import com.github.mkopylec.projectmanager.core.team.TeamRepository;
import com.github.mkopylec.projectmanager.core.team.TeamService;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_NEW_PROJECT;
import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_NEW_PROJECT_DRAFT;
import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_NEW_TEAM;
import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_NEW_TEAM_MEMBER;
import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_PROJECT_ENDING_CONDITION;
import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.core.ErrorCode.EMPTY_UPDATED_PROJECT;
import static com.github.mkopylec.projectmanager.core.InputDataRequirementsValidator.requirements;
import static com.github.mkopylec.projectmanager.core.ProjectManagerException.projectManagerException;

/**
 * Primary port
 */
public class ProjectManager {

    private ProjectManagerService service;

    public ProjectManager(UniqueIdentifierGenerator identifierGenerator, ProjectRepository projectRepository, EventPublisher eventPublisher, TeamRepository teamRepository) {
        var projectService = new ProjectService(identifierGenerator, projectRepository, eventPublisher);
        var teamService = new TeamService(teamRepository);
        service = new ProjectManagerService(projectService, teamService);
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        try {
            requirements()
                    .require(newProjectDraft, EMPTY_NEW_PROJECT_DRAFT)
                    .validate();
            service.createProject(newProjectDraft);
        } catch (Exception e) {
            throw projectManagerException(e, "Creating '" + newProjectDraft.getName() + "' project has failed");
        }
    }

    public void createProject(NewProject newProject) {
        try {
            requirements()
                    .require(newProject, EMPTY_NEW_PROJECT)
                    .validate();
            service.createProject(newProject);
        } catch (Exception e) {
            throw projectManagerException(e, "Creating '" + newProject.getName() + "' project has failed");
        }
    }

    public List<ExistingProjectDraft> getProjects() {
        try {
            return service.getProjects();
        } catch (Exception e) {
            throw projectManagerException(e, "Getting projects has failed");
        }
    }

    public ExistingProject getProject(String projectIdentifier) {
        try {
            requirements()
                    .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                    .validate();
            return service.getProject(projectIdentifier);
        } catch (Exception e) {
            throw projectManagerException(e, "Getting '" + projectIdentifier + "' project has failed");
        }
    }

    public void updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        try {
            requirements()
                    .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                    .require(updatedProject, EMPTY_UPDATED_PROJECT)
                    .validate();
            service.updateProject(projectIdentifier, updatedProject);
        } catch (Exception e) {
            throw projectManagerException(e, "Updating '" + projectIdentifier + "' project has failed");
        }
    }

    public void startProject(String projectIdentifier) {
        try {
            requirements()
                    .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                    .validate();
            service.startProject(projectIdentifier);
        } catch (Exception e) {
            throw projectManagerException(e, "Starting '" + projectIdentifier + "' project has failed");
        }
    }

    public void endProject(String projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        try {
            requirements()
                    .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                    .require(projectEndingCondition, EMPTY_PROJECT_ENDING_CONDITION)
                    .validate();
            service.endProject(projectIdentifier, projectEndingCondition);
        } catch (Exception e) {
            throw projectManagerException(e, "Ending '" + projectIdentifier + "' project has failed");
        }
    }

    public void createTeam(NewTeam newTeam) {
        try {
            requirements()
                    .require(newTeam, EMPTY_NEW_TEAM)
                    .validate();
            service.createTeam(newTeam);
        } catch (Exception e) {
            throw projectManagerException(e, "Creating '" + newTeam.getName() + "' team has failed");
        }
    }

    public void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        try {
            requirements()
                    .require(teamName, EMPTY_TEAM_NAME)
                    .require(newTeamMember, EMPTY_NEW_TEAM_MEMBER)
                    .validate();
            service.addMemberToTeam(teamName, newTeamMember);
        } catch (Exception e) {
            throw projectManagerException(e, "Adding member to '" + teamName + "' team has failed");
        }
    }

    public List<ExistingTeam> getTeams() {
        try {
            return service.getTeams();
        } catch (Exception e) {
            throw projectManagerException(e, "Getting teams has failed");
        }
    }
}

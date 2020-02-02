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

    private ProjectService projectService;
    private TeamService teamService;
    private OutgoingDtoMapper dtoMapper = new OutgoingDtoMapper();

    public ProjectManager(UniqueIdentifierGenerator identifierGenerator, ProjectRepository projectRepository, EventPublisher eventPublisher, TeamRepository teamRepository) {
        projectService = new ProjectService(identifierGenerator, projectRepository, eventPublisher);
        teamService = new TeamService(teamRepository);
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        try {
            requirements()
                    .require(newProjectDraft, EMPTY_NEW_PROJECT_DRAFT)
                    .validate();
            projectService.createProject(newProjectDraft);
        } catch (Exception e) {
            throw projectManagerException(e, "Creating '" + newProjectDraft.getName() + "' project has failed");
        }
    }

    public void createProject(NewProject newProject) {
        try {
            requirements()
                    .require(newProject, EMPTY_NEW_PROJECT)
                    .validate();
            projectService.createProject(newProject);
        } catch (Exception e) {
            throw projectManagerException(e, "Creating '" + newProject.getName() + "' project has failed");
        }
    }

    public List<ExistingProjectDraft> getProjects() {
        try {
            var projects = projectService.getProjects();
            return dtoMapper.mapToExistingProjectDrafts(projects);
        } catch (Exception e) {
            throw projectManagerException(e, "Getting projects has failed");
        }
    }

    public ExistingProject getProject(String projectIdentifier) {
        try {
            requirements()
                    .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                    .validate();
            var project = projectService.getProject(projectIdentifier);
            return dtoMapper.mapToExistingProject(project);
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
            teamService.ensureTeamAssignedToProjectExists(updatedProject);
            var project = projectService.updateProject(projectIdentifier, updatedProject);
            teamService.addImplementedProjectToTeam(project);
        } catch (Exception e) {
            throw projectManagerException(e, "Updating '" + projectIdentifier + "' project has failed");
        }
    }

    public void startProject(String projectIdentifier) {
        try {
            requirements()
                    .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                    .validate();
            projectService.startProject(projectIdentifier);
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
            var project = projectService.endProject(projectIdentifier, projectEndingCondition);
            teamService.removeImplementedProjectFromTeam(project);
        } catch (Exception e) {
            throw projectManagerException(e, "Ending '" + projectIdentifier + "' project has failed");
        }
    }

    public void createTeam(NewTeam newTeam) {
        try {
            requirements()
                    .require(newTeam, EMPTY_NEW_TEAM)
                    .validate();
            teamService.createTeam(newTeam);
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
            teamService.addMemberToTeam(teamName, newTeamMember);
        } catch (Exception e) {
            throw projectManagerException(e, "Adding member to '" + teamName + "' team has failed");
        }
    }

    public List<ExistingTeam> getTeams() {
        try {
            var teams = teamService.getTeams();
            return dtoMapper.mapToExistingTeams(teams);
        } catch (Exception e) {
            throw projectManagerException(e, "Getting teams has failed");
        }
    }
}

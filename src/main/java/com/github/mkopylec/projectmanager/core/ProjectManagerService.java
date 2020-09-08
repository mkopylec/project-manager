package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.project.ProjectService;
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

class ProjectManagerService {

    private ProjectService projectService;
    private TeamService teamService;
    private OutgoingDtoMapper dtoMapper = new OutgoingDtoMapper();

    ProjectManagerService(ProjectService projectService, TeamService teamService) {
        this.projectService = projectService;
        this.teamService = teamService;
    }

    void createProject(NewProjectDraft newProjectDraft) {
        requirements()
                .require(newProjectDraft, EMPTY_NEW_PROJECT_DRAFT)
                .validate();
        projectService.createProject(newProjectDraft);
    }

    void createProject(NewProject newProject) {
        requirements()
                .require(newProject, EMPTY_NEW_PROJECT)
                .validate();
        projectService.createProject(newProject);
    }

    List<ExistingProjectDraft> getProjects() {
        var projects = projectService.getProjects();
        return dtoMapper.mapToExistingProjectDrafts(projects);
    }

    ExistingProject getProject(String projectIdentifier) {
        requirements()
                .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                .validate();
        var project = projectService.getProject(projectIdentifier);
        return dtoMapper.mapToExistingProject(project);
    }

    void updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        requirements()
                .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                .require(updatedProject, EMPTY_UPDATED_PROJECT)
                .validate();
        var project = projectService.updateProject(projectIdentifier, updatedProject); // Breaks CQS because of saving inside the service method.
        teamService.addImplementedProjectToTeam(project);
    }

    void startProject(String projectIdentifier) {
        requirements()
                .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                .validate();
        projectService.startProject(projectIdentifier);
    }

    void endProject(String projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        requirements()
                .require(projectIdentifier, EMPTY_PROJECT_IDENTIFIER)
                .require(projectEndingCondition, EMPTY_PROJECT_ENDING_CONDITION)
                .validate();
        var project = projectService.endProject(projectIdentifier, projectEndingCondition); // Breaks CQS because of saving inside the service method.
        teamService.removeImplementedProjectFromTeam(project);
    }

    void createTeam(NewTeam newTeam) {
        requirements()
                .require(newTeam, EMPTY_NEW_TEAM)
                .validate();
        teamService.createTeam(newTeam);
    }

    void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        requirements()
                .require(teamName, EMPTY_TEAM_NAME)
                .require(newTeamMember, EMPTY_NEW_TEAM_MEMBER)
                .validate();
        teamService.getTeamWithAddedMember(teamName, newTeamMember);
    }

    List<ExistingTeam> getTeams() {
        var teams = teamService.getTeams();
        return dtoMapper.mapToExistingTeams(teams);
    }
}

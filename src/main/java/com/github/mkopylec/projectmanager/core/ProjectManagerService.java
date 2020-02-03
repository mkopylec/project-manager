package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.team.TeamService;

import java.util.List;

class ProjectManagerService {

    private ProjectService projectService;
    private TeamService teamService;
    private OutgoingDtoMapper dtoMapper = new OutgoingDtoMapper();

    ProjectManagerService(ProjectService projectService, TeamService teamService) {
        this.projectService = projectService;
        this.teamService = teamService;
    }

    void createProject(NewProjectDraft newProjectDraft) {
        var project = projectService.createProject(newProjectDraft);
        projectService.saveProject(project);
    }

    void createProject(NewProject newProject) {
        var project = projectService.createProject(newProject);
        projectService.saveProject(project);
    }

    List<ExistingProjectDraft> getProjects() {
        var projects = projectService.getProjects();
        return dtoMapper.mapToExistingProjectDrafts(projects);
    }

    ExistingProject getProject(String projectIdentifier) {
        var project = projectService.getProject(projectIdentifier);
        return dtoMapper.mapToExistingProject(project);
    }

    void updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        var project = projectService.getUpdatedProject(projectIdentifier, updatedProject);
        var team = teamService.getTeamWithAddedImplementedProject(project);
        projectService.saveProject(project); // Cannot save inside previous invocations cause they may fail because of validation errors
        teamService.saveTeam(team);
    }

    void startProject(String projectIdentifier) {
        var project = projectService.getStartedProject(projectIdentifier);
        projectService.saveProject(project);
    }

    void endProject(String projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        var project = projectService.getEndedProject(projectIdentifier, projectEndingCondition);
        var team = teamService.getTeamWithRemovedImplementedProject(project);
        projectService.saveProject(project);
        teamService.saveTeam(team);
    }

    void createTeam(NewTeam newTeam) {
        var team = teamService.createTeam(newTeam);
        teamService.saveTeam(team);
    }

    void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        var team = teamService.getTeamWithAddedMember(teamName, newTeamMember);
        teamService.saveTeam(team);
    }

    List<ExistingTeam> getTeams() {
        var teams = teamService.getTeams();
        return dtoMapper.mapToExistingTeams(teams);
    }
}

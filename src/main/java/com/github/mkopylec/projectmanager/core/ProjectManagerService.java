package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.api.ProjectManager;
import com.github.mkopylec.projectmanager.api.dto.ExistingProject;
import com.github.mkopylec.projectmanager.api.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.api.dto.NewProject;
import com.github.mkopylec.projectmanager.api.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.NewTeam;
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember;
import com.github.mkopylec.projectmanager.api.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.api.dto.UpdatedProject;
import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.team.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectManagerService implements ProjectManager {

    private ProjectService projectService;
    private TeamService teamService;
    private OutgoingDtoMapper dtoMapper;

    public ProjectManagerService(ProjectService projectService, TeamService teamService, OutgoingDtoMapper dtoMapper) {
        this.projectService = projectService;
        this.teamService = teamService;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public void createProject(NewProjectDraft newProjectDraft) {
        var project = projectService.createProject(newProjectDraft);
        projectService.saveProject(project);
    }

    @Override
    public void createProject(NewProject newProject) {
        var project = projectService.createProject(newProject);
        projectService.saveProject(project);
    }

    @Override
    public List<ExistingProjectDraft> getProjects() {
        var projects = projectService.getProjects();
        return dtoMapper.mapToExistingProjectDrafts(projects);
    }

    @Override
    public ExistingProject getProject(UUID projectIdentifier) {
        var project = projectService.getProject(projectIdentifier);
        return dtoMapper.mapToExistingProject(project);
    }

    @Override
    public void updateProject(UUID projectIdentifier, UpdatedProject updatedProject) {
        var project = projectService.getUpdatedProject(projectIdentifier, updatedProject);
        var team = teamService.getTeamWithAddedImplementedProject(project);
        projectService.saveProject(project); // Cannot save inside previous invocations cause they may fail because of validation errors. Use "unit of work" pattern
        teamService.saveTeam(team);
    }

    @Override
    public void startProject(UUID projectIdentifier) {
        var project = projectService.getStartedProject(projectIdentifier);
        projectService.saveProject(project);
    }

    @Override
    public void endProject(UUID projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        var project = projectService.getEndedProject(projectIdentifier, projectEndingCondition);
        var team = teamService.getTeamWithRemovedImplementedProject(project);
        projectService.saveProject(project);
        teamService.saveTeam(team);
    }

    @Override
    public void createTeam(NewTeam newTeam) {
        var team = teamService.createTeam(newTeam);
        teamService.saveTeam(team);
    }

    @Override
    public void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        var team = teamService.getTeamWithAddedMember(teamName, newTeamMember);
        teamService.saveTeam(team);
    }

    @Override
    public List<ExistingTeam> getTeams() {
        var teams = teamService.getTeams();
        return dtoMapper.mapToExistingTeams(teams);
    }
}

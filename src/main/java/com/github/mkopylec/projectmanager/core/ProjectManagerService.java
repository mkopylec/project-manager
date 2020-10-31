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
        projectService.createProject(newProjectDraft);
    }

    @Override
    public void createProject(NewProject newProject) {
        projectService.createProject(newProject);
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
        var project = projectService.updateProject(projectIdentifier, updatedProject);
        teamService.addTeamImplementedProject(project);
    }

    @Override
    public void startProject(UUID projectIdentifier) {
        projectService.startProject(projectIdentifier);
    }

    @Override
    public void endProject(UUID projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        var project = projectService.endProject(projectIdentifier, projectEndingCondition);
        teamService.removeTeamImplementedProject(project);
    }

    @Override
    public void createTeam(NewTeam newTeam) {
        teamService.createTeam(newTeam);
    }

    @Override
    public void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        teamService.addTeamMember(teamName, newTeamMember);
    }

    @Override
    public List<ExistingTeam> getTeams() {
        var teams = teamService.getTeams();
        return dtoMapper.mapToExistingTeams(teams);
    }
}

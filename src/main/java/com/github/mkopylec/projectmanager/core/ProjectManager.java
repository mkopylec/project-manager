package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.EventPublisher;
import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.project.UniqueIdentifierGenerator;
import com.github.mkopylec.projectmanager.core.team.Team;
import com.github.mkopylec.projectmanager.core.team.TeamRepository;
import com.github.mkopylec.projectmanager.core.team.TeamService;

import java.util.List;

import static java.util.Objects.requireNonNull;

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
        requireNonNull(newProjectDraft, "Empty new project draft");
        projectService.createProject(newProjectDraft);
    }

    public void createProject(NewProject newProject) {
        requireNonNull(newProject, "Empty new project");
        projectService.createProject(newProject);
    }

    public List<ExistingProjectDraft> getProjects() {
        List<Project> projects = projectService.getProjects();
        return dtoMapper.mapToExistingProjectDrafts(projects);
    }

    public ExistingProject getProject(String projectIdentifier) {
        Project project = projectService.getProject(projectIdentifier);
        return dtoMapper.mapToExistingProject(project);
    }

    public void updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        requireNonNull(updatedProject, "Empty updated project");
        teamService.ensureTeamAssignedToProjectExists(projectIdentifier, updatedProject);
        Project project = projectService.updateProject(projectIdentifier, updatedProject);
        teamService.addImplementedProjectToTeam(project);
    }

    public void startProject(String projectIdentifier) {
        projectService.startProject(projectIdentifier);
    }

    public void endProject(String projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        requireNonNull(projectEndingCondition, "Empty project ending condition");
        Project project = projectService.endProject(projectIdentifier, projectEndingCondition);
        teamService.removeImplementedProjectFromTeam(project);
    }

    public void createTeam(NewTeam newTeam) {
        requireNonNull(newTeam, "Empty new team");
        teamService.createTeam(newTeam);
    }

    public void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        requireNonNull(newTeamMember, "Empty new team member");
        teamService.addMemberToTeam(teamName, newTeamMember);
    }

    public List<ExistingTeam> getTeams() {
        List<Team> teams = teamService.getTeams();
        return dtoMapper.mapToExistingTeams(teams);
    }
}

package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.TeamAssignedToProject;
import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.NewProject;
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProject;
import com.github.mkopylec.projectmanager.core.team.TeamService;
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam;
import com.github.mkopylec.projectmanager.core.team.dto.TeamMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProjectManagerFacade implements ProjectManager {

    private ProjectService projectService;
    private TeamService teamService;

    ProjectManagerFacade(ProjectService projectService, TeamService teamService) {
        this.projectService = projectService;
        this.teamService = teamService;
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
        return projectService.getProjects();
    }

    @Override
    public ExistingProject getProject(String projectIdentifier) {
        return projectService.getProject(projectIdentifier);
    }

    @Override
    public void updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        TeamAssignedToProject teamAssignedToProject = projectService.updateProject(projectIdentifier, updatedProject);
        teamService.addImplementedProjectToTeam(teamAssignedToProject);
    }

    @Override
    public void startProject(String projectIdentifier) {
        projectService.startProject(projectIdentifier);
    }

    @Override
    public void endProject(String projectIdentifier, ProjectEndingCondition endingCondition) {
        TeamAssignedToProject teamAssignedToProject = projectService.endProject(projectIdentifier, endingCondition);
        teamService.removeImplementedProjectFromTeam(teamAssignedToProject);
    }

    @Override
    public void createTeam(NewTeam newTeam) {
        teamService.createTeam(newTeam);
    }

    @Override
    public void addMemberToTeam(String teamName, TeamMember teamMember) {
        teamService.addMemberToTeam(teamName, teamMember);
    }

    @Override
    public List<ExistingTeam> getTeams() {
        return teamService.getTeams();
    }
}

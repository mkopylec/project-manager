package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.NewProject;
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProject;
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam;
import com.github.mkopylec.projectmanager.core.team.dto.TeamMember;

import java.util.List;

/**
 * Primary port
 */
public interface ProjectManager {

    void createProject(NewProjectDraft newProjectDraft);

    void createProject(NewProject newProject);

    List<ExistingProjectDraft> getProjects();

    ExistingProject getProject(String projectIdentifier);

    void updateProject(String projectIdentifier, UpdatedProject updatedProject);

    void startProject(String projectIdentifier);

    void endProject(String projectIdentifier, ProjectEndingCondition endingCondition);

    void createTeam(NewTeam newTeam);

    void addMemberToTeam(String teamName, TeamMember teamMember);

    List<ExistingTeam> getTeams();
}

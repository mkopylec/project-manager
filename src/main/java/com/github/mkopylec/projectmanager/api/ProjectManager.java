package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.api.dto.ExistingProject;
import com.github.mkopylec.projectmanager.api.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.api.dto.NewProject;
import com.github.mkopylec.projectmanager.api.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.NewTeam;
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember;
import com.github.mkopylec.projectmanager.api.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.api.dto.UpdatedProject;

import java.util.List;
import java.util.UUID;

public interface ProjectManager {

    void createProject(NewProjectDraft newProjectDraft);

    void createProject(NewProject newProject);

    List<ExistingProjectDraft> getProjects();

    ExistingProject getProject(UUID projectIdentifier);

    void updateProject(UUID projectIdentifier, UpdatedProject updatedProject);

    void startProject(UUID projectIdentifier);

    void endProject(UUID projectIdentifier, ProjectEndingCondition projectEndingCondition);

    void createTeam(NewTeam newTeam);

    void addMemberToTeam(String teamName, NewTeamMember newTeamMember);

    List<ExistingTeam> getTeams();
}

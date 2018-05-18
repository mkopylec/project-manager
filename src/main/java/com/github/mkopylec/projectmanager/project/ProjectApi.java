package com.github.mkopylec.projectmanager.project;

import com.github.mkopylec.projectmanager.application.dto.ExistingProject;
import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.NewProject;
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.application.dto.UpdatedProject;

import java.util.List;

public interface ProjectApi {

    void createProject(NewProjectDraft newProjectDraft);

    void createProject(NewProject newProject);

    List<ExistingProjectDraft> getProjects();

    ExistingProject getProject(String projectIdentifier);

    void updateProject(String projectIdentifier, UpdatedProject updatedProject);

    void startProject(String projectIdentifier);

    void endProject(String projectIdentifier, ProjectEndingCondition endingCondition);
}

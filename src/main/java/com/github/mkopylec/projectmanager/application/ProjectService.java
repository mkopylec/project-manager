package com.github.mkopylec.projectmanager.application;

import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.project.ProjectFactory;
import com.github.mkopylec.projectmanager.domain.project.ProjectRepository;

import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectFactory projectFactory;
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectFactory projectFactory, ProjectRepository projectRepository) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        Project project = projectFactory.createProjectDraft(newProjectDraft.getName());
        projectRepository.save(project);
    }
}

package com.github.mkopylec.projectmanager.application;

import java.util.List;

import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.NewProject;
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.project.ProjectFactory;
import com.github.mkopylec.projectmanager.domain.project.ProjectRepository;
import com.github.mkopylec.projectmanager.domain.values.Feature;

import org.springframework.stereotype.Service;

import static com.github.mkopylec.projectmanager.application.utils.DtoMapper.mapToExistingProjectDrafts;
import static com.github.mkopylec.projectmanager.application.utils.DtoMapper.mapToFeatures;

@Service
public class ProjectService {

    private ProjectFactory projectFactory;
    private ProjectRepository projectRepository;

    public ProjectService(ProjectFactory projectFactory, ProjectRepository projectRepository) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        Project project = projectFactory.createProjectDraft(newProjectDraft.getName());
        projectRepository.save(project);
    }

    public void createProject(NewProject newProject) {
        List<Feature> features = mapToFeatures(newProject.getFeatures());
        Project project = projectFactory.createFullProject(newProject.getName(), features);
        projectRepository.save(project);
    }

    public List<ExistingProjectDraft> getProjects() {
        List<Project> projects = projectRepository.findAll();
        return mapToExistingProjectDrafts(projects);
    }
}

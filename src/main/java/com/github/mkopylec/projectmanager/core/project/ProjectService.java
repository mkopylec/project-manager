package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.api.dto.NewProject;
import com.github.mkopylec.projectmanager.api.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.api.dto.UpdatedProject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.api.exception.MissingEntityException.requireExisting;
import static com.github.mkopylec.projectmanager.core.project.FeatureChecker.featureChecker;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.MISSING_PROJECT;

@Service
public class ProjectService {

    private ProjectFactory factory;
    private ProjectRepository repository;
    private EndedProjectsReporter endedProjectsReporter;

    public ProjectService(ProjectFactory factory, ProjectRepository repository, EndedProjectsReporter endedProjectsReporter) {
        this.factory = factory;
        this.repository = repository;
        this.endedProjectsReporter = endedProjectsReporter;
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        var project = factory.createProjectDraft(newProjectDraft);
        repository.save(project);
    }

    public void createProject(NewProject newProject) {
        var project = factory.createFullProject(newProject);
        repository.save(project);
    }

    public List<Project> getProjects() {
        return repository.findAll();
    }

    public Project getProject(UUID projectIdentifier) {
        var project = repository.findByIdentifier(projectIdentifier);
        requireExisting(project, MISSING_PROJECT);
        return project;
    }

    public Project updateProject(UUID projectIdentifier, UpdatedProject updatedProject) {
        var project = getProject(projectIdentifier);
        var features = factory.createFeatures(updatedProject.getFeatures());
        project.rename(updatedProject.getName());
        project.updateFeatures(features);
        project.assignTeam(updatedProject.getTeam());
        repository.save(project);
        return project;
    }

    public void startProject(UUID projectIdentifier) {
        var project = getProject(projectIdentifier);
        project.start();
        repository.save(project);
    }

    public Project endProject(UUID projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        var project = getProject(projectIdentifier);
        var featureChecker = featureChecker(projectEndingCondition.isOnlyNecessaryFeatureDone());
        var endedProject = project.end(featureChecker);
        repository.save(project);
        endedProjectsReporter.report(endedProject);
        return project;
    }
}

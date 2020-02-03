package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.NewProject;
import com.github.mkopylec.projectmanager.core.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.core.UpdatedProject;
import com.github.mkopylec.projectmanager.core.common.EventPublisher;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isNotEmpty;
import static com.github.mkopylec.projectmanager.core.project.FeatureChecker.featureChecker;
import static com.github.mkopylec.projectmanager.core.project.ProjectRequirementsValidator.requirements;

public class ProjectService {

    private ProjectFactory factory;
    private ProjectRepository repository;
    private EventPublisher eventPublisher;

    public ProjectService(UniqueIdentifierGenerator identifierGenerator, ProjectRepository repository, EventPublisher eventPublisher) {
        factory = new ProjectFactory(identifierGenerator);
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Project createProject(NewProjectDraft newProjectDraft) {
        return factory.createProjectDraft(newProjectDraft);
    }

    public Project createProject(NewProject newProject) {
        return factory.createFullProject(newProject);
    }

    public List<Project> getProjects() {
        return repository.findAll();
    }

    public Project getProject(String projectIdentifier) {
        var project = repository.findByIdentifier(projectIdentifier);
        requirements()
                .requireProject(project)
                .validate();
        return project;
    }

    public Project getUpdatedProject(String projectIdentifier, UpdatedProject updatedProject) {
        var project = repository.findByIdentifier(projectIdentifier);
        requirements()
                .requireProject(project)
                .validate();
        var features = factory.createFeatures(updatedProject.getFeatures());
        project.rename(updatedProject.getName());
        project.updateFeatures(features);
        project.assignTeam(updatedProject.getTeam());
        return project;
    }

    public Project getStartedProject(String projectIdentifier) {
        var project = repository.findByIdentifier(projectIdentifier);
        requirements()
                .requireProject(project)
                .validate();
        project.start();
        return project;
    }

    public Project getEndedProject(String projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        var project = repository.findByIdentifier(projectIdentifier);
        requirements()
                .requireProject(project)
                .validate();
        var featureChecker = featureChecker(projectEndingCondition.isOnlyNecessaryFeatureDone());
        var endedProject = project.end(featureChecker);
        eventPublisher.publish(endedProject);
        return project;
    }

    public void saveProject(Project project) {
        if (isNotEmpty(project)) {
            repository.save(project);
        }
    }
}

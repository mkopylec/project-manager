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
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isNotEmpty;

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

    public Project createProject(NewProjectDraft newProjectDraft) {
        return factory.createProjectDraft(newProjectDraft);
    }

    public Project createProject(NewProject newProject) {
        return factory.createFullProject(newProject);
    }

    public List<Project> getProjects() {
        return repository.findAll();
    }

    public Project getProject(UUID projectIdentifier) {
        var project = repository.findByIdentifier(projectIdentifier);
        requireExisting(project, MISSING_PROJECT);
        return project;
    }

    public Project getUpdatedProject(UUID projectIdentifier, UpdatedProject updatedProject) {
        var project = getProject(projectIdentifier);
        var features = factory.createFeatures(updatedProject.getFeatures());
        project.rename(updatedProject.getName());
        project.updateFeatures(features);
        project.assignTeam(updatedProject.getTeam());
        return project;
    }

    public Project getStartedProject(UUID projectIdentifier) {
        var project = getProject(projectIdentifier);
        project.start();
        return project;
    }

    public Project getEndedProject(UUID projectIdentifier, ProjectEndingCondition projectEndingCondition) {
        var project = getProject(projectIdentifier);
        var featureChecker = featureChecker(projectEndingCondition.isOnlyNecessaryFeatureDone());
        var endedProject = project.end(featureChecker);
        endedProjectsReporter.report(endedProject);
        return project;
    }

    public void saveProject(Project project) {
        if (isNotEmpty(project)) {
            repository.save(project);
        }
    }
}

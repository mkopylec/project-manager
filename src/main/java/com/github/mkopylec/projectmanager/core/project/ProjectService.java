package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.TeamAssignedToProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.NewProject;
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.DtoMapper.mapNewToFeatures;
import static com.github.mkopylec.projectmanager.core.project.DtoMapper.mapToExistingProject;
import static com.github.mkopylec.projectmanager.core.project.DtoMapper.mapToExistingProjectDrafts;
import static com.github.mkopylec.projectmanager.core.project.DtoMapper.mapToFeatures;
import static com.github.mkopylec.projectmanager.core.project.DtoMapper.mapToTeamAssignedToProject;
import static com.github.mkopylec.projectmanager.core.project.FeatureChecker.resolveFeatureChecker;
import static com.github.mkopylec.projectmanager.core.project.PreCondition.when;

@Service
public class ProjectService {

    private ProjectFactory projectFactory;
    private ProjectRepository projectRepository;
    private ApplicationEventPublisher eventPublisher;

    ProjectService(ProjectFactory projectFactory, ProjectRepository projectRepository, ApplicationEventPublisher eventPublisher) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
        this.eventPublisher = eventPublisher;
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        Project project = projectFactory.createProjectDraft(newProjectDraft.getName());
        projectRepository.save(project);
    }

    public void createProject(NewProject newProject) {
        List<Feature> features = mapNewToFeatures(newProject.getFeatures());
        Project project = projectFactory.createFullProject(newProject.getName(), features);
        projectRepository.save(project);
    }

    public List<ExistingProjectDraft> getProjects() {
        List<Project> projects = projectRepository.findAll();
        return mapToExistingProjectDrafts(projects);
    }

    public ExistingProject getProject(String projectIdentifier) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingProject("Error getting '" + projectIdentifier + "' project");
        return mapToExistingProject(project);
    }

    public TeamAssignedToProject updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingProject("Error updating '" + projectIdentifier + "' project");
        List<Feature> features = mapToFeatures(updatedProject.getFeatures());
        project.rename(updatedProject.getName());
        project.updateFeatures(features);
        project.assignTeam(updatedProject.getTeam());
        projectRepository.save(project);
        return mapToTeamAssignedToProject(project);
    }

    public void startProject(String projectIdentifier) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingProject("Error starting '" + projectIdentifier + "' project");
        project.start();
        projectRepository.save(project);
    }

    public TeamAssignedToProject endProject(String projectIdentifier, ProjectEndingCondition endingCondition) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingProject("Error ending '" + projectIdentifier + "' project");
        FeatureChecker featureChecker = resolveFeatureChecker(endingCondition.isOnlyNecessaryFeatureDone());
        EndedProject endedProject = project.end(featureChecker);
        projectRepository.save(project);
        eventPublisher.publishEvent(endedProject);
        return mapToTeamAssignedToProject(project);
    }
}

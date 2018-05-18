package com.github.mkopylec.projectmanager.project;

import com.github.mkopylec.projectmanager.application.dto.ExistingProject;
import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.NewProject;
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.application.dto.UpdatedProject;
import com.github.mkopylec.projectmanager.application.utils.DtoMapper;
import com.github.mkopylec.projectmanager.domain.project.FeatureChecker;
import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.project.ProjectFactory;
import com.github.mkopylec.projectmanager.domain.project.ProjectRepository;
import com.github.mkopylec.projectmanager.domain.services.ProjectTeamAssigner;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;
import com.github.mkopylec.projectmanager.domain.values.Feature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.mkopylec.projectmanager.application.utils.DtoMapper.mapToExistingProject;
import static com.github.mkopylec.projectmanager.application.utils.DtoMapper.mapToExistingProjectDrafts;
import static com.github.mkopylec.projectmanager.application.utils.DtoMapper.mapToFeatures;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.NONEXISTENT_PROJECT;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.NO_PROJECTS_EXIST;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static com.github.mkopylec.projectmanager.domain.project.FeatureChecker.resolveFeatureChecker;

@Service
class ProjectService implements ProjectApi {

    private ProjectFactory projectFactory;
    private ProjectRepository projectRepository;
    private TeamRepository teamRepository;
    private ProjectTeamAssigner projectTeamAssigner;
    private ApplicationEventPublisher eventPublisher;

    ProjectService(ProjectFactory projectFactory, ProjectRepository projectRepository, TeamRepository teamRepository, ProjectTeamAssigner projectTeamAssigner, ApplicationEventPublisher eventPublisher) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.projectTeamAssigner = projectTeamAssigner;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void createProject(NewProjectDraft newProjectDraft) {
        Project project = projectFactory.createProjectDraft(newProjectDraft.getName());
        projectRepository.save(project);
    }

    @Override
    public void createProject(NewProject newProject) {
        List<Feature> features = DtoMapper.mapNewToFeatures(newProject.getFeatures());
        Project project = projectFactory.createFullProject(newProject.getName(), features);
        projectRepository.save(project);
    }

    @Override
    public List<ExistingProjectDraft> getProjects() {
        List<Project> projects = projectRepository.findAll();
        when(projects.isEmpty())
                .thenMissingEntity(NO_PROJECTS_EXIST, "Error getting projects");
        return mapToExistingProjectDrafts(projects);
    }

    @Override
    public ExistingProject getProject(String projectIdentifier) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingEntity(NONEXISTENT_PROJECT, "Error getting '" + projectIdentifier + "' project");
        return mapToExistingProject(project);
    }

    @Override
    public void updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingEntity(NONEXISTENT_PROJECT, "Error updating '" + projectIdentifier + "' project");
        List<Feature> features = mapToFeatures(updatedProject.getFeatures());
        project.rename(updatedProject.getName());
        project.updateFeatures(features);
        Team team = teamRepository.findByName(updatedProject.getTeam());
        projectTeamAssigner.assignTeamToProject(team, project);
        projectRepository.save(project);
        teamRepository.save(team);
    }

    @Override
    public void startProject(String projectIdentifier) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingEntity(NONEXISTENT_PROJECT, "Error starting '" + projectIdentifier + "' project");
        project.start();
        projectRepository.save(project);
    }

    @Override
    public void endProject(String projectIdentifier, ProjectEndingCondition endingCondition) {
        Project project = projectRepository.findByIdentifier(projectIdentifier);
        when(project == null)
                .thenMissingEntity(NONEXISTENT_PROJECT, "Error ending '" + projectIdentifier + "' project");
        FeatureChecker featureChecker = resolveFeatureChecker(endingCondition.isOnlyNecessaryFeatureDone());
        project.end(featureChecker, eventPublisher);
        projectRepository.save(project);
    }
}

package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.UseCaseService;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.NewProject;
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProject;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isNotEmpty;
import static com.github.mkopylec.projectmanager.core.project.AssignedTeamRequirementsValidator.assignedTeamRequirements;
import static com.github.mkopylec.projectmanager.core.project.FeatureChecker.featureChecker;
import static com.github.mkopylec.projectmanager.core.project.ProjectRequirementsValidator.projectRequirements;

public class ProjectService extends UseCaseService {

    private OutgoingDtoMapper dtoMapper = new OutgoingDtoMapper();
    private ProjectFactory projectFactory;
    private ProjectRepository projectRepository;
    private AssignedTeamRepository teamRepository;
    private EventPublisher eventPublisher;

    public ProjectService(UniqueIdentifierGenerator identifierGenerator, ProjectRepository projectRepository, AssignedTeamRepository teamRepository, EventPublisher eventPublisher) {
        projectFactory = new ProjectFactory(identifierGenerator);
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.eventPublisher = eventPublisher;
    }

    public void createProject(NewProjectDraft newProjectDraft) {
        executeUseCase("create project draft", newProjectDraft, () -> {
            var project = projectFactory.createProjectDraft(newProjectDraft);
            projectRepository.save(project);
        });
    }

    public void createProject(NewProject newProject) {
        executeUseCase("create project", newProject, () -> {
            var project = projectFactory.createFullProject(newProject);
            projectRepository.save(project);
        });
    }

    public List<ExistingProjectDraft> getProjects() {
        return executeUseCase("get projects", () -> {
            var projects = projectRepository.findAll();
            return dtoMapper.mapToExistingProjectDrafts(projects);
        });
    }

    public ExistingProject getProject(String projectIdentifier) {
        return executeUseCase("get project", projectIdentifier, () -> {
            var project = requireProject(projectIdentifier);
            return dtoMapper.mapToExistingProject(project);
        });
    }

    public void updateProject(UpdatedProject updatedProject) {
        executeUseCase("update project", updatedProject, () -> {
            var project = requireProject(updatedProject.getIdentifier());
            var features = projectFactory.createFeatures(updatedProject.getFeatures());
            project.rename(updatedProject.getName());
            project.updateFeatures(features);
            project.assignTeam(updatedProject.getTeam());
            var team = getTeamAssignedToProject(project);
            if (isNotEmpty(team)) {
                team.addCurrentlyImplementedProject();
                teamRepository.save(team);
            }
            projectRepository.save(project);
        });
    }

    public void startProject(String projectIdentifier) {
        executeUseCase("start project", projectIdentifier, () -> {
            var project = requireProject(projectIdentifier);
            project.start();
            projectRepository.save(project);
        });
    }

    public void endProject(ProjectEndingCondition projectEndingCondition) {
        executeUseCase("end project", projectEndingCondition, () -> {
            var project = requireProject(projectEndingCondition.getIdentifier());
            var featureChecker = featureChecker(projectEndingCondition.isOnlyNecessaryFeatureDone());
            var endedProject = project.end(featureChecker);
            var team = getTeamAssignedToProject(project);
            if (isNotEmpty(team)) {
                team.removeCurrentlyImplementedProject();
                teamRepository.save(team);
            }
            projectRepository.save(project);
            eventPublisher.publish(endedProject);
        });
    }

    private Project requireProject(String projectIdentifier) {
        var project = projectRepository.findByIdentifier(projectIdentifier);
        projectRequirements()
                .require(project)
                .validate();
        return project;
    }

    private AssignedTeam getTeamAssignedToProject(Project project) {
        if (project.hasNoTeamAssigned()) {
            return null;
        }
        var team = teamRepository.findByName(project.getAssignedTeam());
        assignedTeamRequirements()
                .require(team)
                .validate();
        return team;
    }
}

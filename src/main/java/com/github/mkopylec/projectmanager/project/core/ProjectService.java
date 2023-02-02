package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.EventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

import static com.github.mkopylec.projectmanager.common.support.ListUtils.mapToUnmodifiable;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.NewProject;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.NewProjectDraft;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.ProjectEnding;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.ProjectId;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.UpdatedProject;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.ExistingProject;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.ExistingProjectDraft;

@Service
public class ProjectService {

    private final Clock clock;
    private final EventPublisher eventPublisher;
    private final ProjectRepository repository;

    private ProjectService(Clock clock, EventPublisher eventPublisher, ProjectRepository repository) {
        this.clock = clock;
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    public void createProjectDraft(NewProjectDraft newProjectDraft) {
        try {
            var project = new Project(newProjectDraft.getName());
            repository.save(project, eventPublisher);
        } catch (ProjectBusinessRuleViolation violation) {
            throw new ProjectDraftNotCreated(violation);
        }
    }

    public List<ExistingProjectDraft> loadProjectDrafts() {
        try {
            return repository.findAll().stream().map(ExistingProjectDraft::new).toList();
        } catch (ProjectBusinessRuleViolation violation) {
            throw new ProjectDraftsNotLoaded(violation);
        }
    }

    public void createProject(NewProject newProject) {
        try {
            var features = newProject.getFeatures().stream()
                    .map(it -> new Feature(it.getName(), it.getRequirement()))
                    .toList();
            var project = new Project(newProject.getName(), features);
            repository.save(project, eventPublisher);
        } catch (ProjectBusinessRuleViolation violation) {
            throw new ProjectNotCreated(violation);
        }
    }

    public ExistingProject loadProject(ProjectId projectId) {
        try {
            var project = repository.require(projectId.getProjectId());
            return new ExistingProject(project);
        } catch (ProjectBusinessRuleViolation violation) {
            throw new ProjectNotLoaded(violation);
        }
    }

    public void updateProject(UpdatedProject updatedProject) {
        try {
            var name = updatedProject.getName();
            var features = mapToUnmodifiable(updatedProject.getFeatures(), it -> new Feature(it.getName(), it.getStatus(), it.getRequirement()));
            var team = updatedProject.getTeam();
            var project = repository.require(updatedProject.getId());
            project.rename(name);
            project.updateFeatures(features);
            project.assignTeam(team, clock);
            repository.save(project, eventPublisher); // TODO team.addCurrentlyImplementedProject()
        } catch (ProjectBusinessRuleViolation violation) {
            throw new ProjectNotUpdated(violation);
        }
    }

    public void startProject(ProjectId projectId) {
        try {
            var project = repository.require(projectId.getProjectId());
            project.start();
            repository.save(project, eventPublisher);
        } catch (ProjectBusinessRuleViolation violation) {
            throw new ProjectNotStarted(violation);
        }
    }

    public void endProject(ProjectEnding ending) {
        try {
            var endingFeaturesPolicy = ending.getProjectEndingFeaturesPolicy();
            var project = repository.require(ending.getProjectId());
            project.end(endingFeaturesPolicy, clock);
            repository.save(project, eventPublisher); // TODO team.removeCurrentlyImplementedProject()
        } catch (ProjectBusinessRuleViolation violation) {
            throw new ProjectNotEnded(violation);
        }
    }

    public static final class ProjectDraftNotCreated extends ProjectUseCaseViolation {

        private ProjectDraftNotCreated(ProjectBusinessRuleViolation violation) {
            super(violation);
        }
    }

    public static final class ProjectDraftsNotLoaded extends ProjectUseCaseViolation {

        private ProjectDraftsNotLoaded(ProjectBusinessRuleViolation violation) {
            super(violation);
        }
    }

    public static final class ProjectNotCreated extends ProjectUseCaseViolation {

        private ProjectNotCreated(ProjectBusinessRuleViolation violation) {
            super(violation);
        }
    }

    public static final class ProjectNotLoaded extends ProjectUseCaseViolation {

        private ProjectNotLoaded(ProjectBusinessRuleViolation violation) {
            super(violation);
        }
    }

    public static final class ProjectNotUpdated extends ProjectUseCaseViolation {

        private ProjectNotUpdated(ProjectBusinessRuleViolation violation) {
            super(violation);
        }
    }

    public static final class ProjectNotStarted extends ProjectUseCaseViolation {

        private ProjectNotStarted(ProjectBusinessRuleViolation violation) {
            super(violation);
        }
    }

    public static final class ProjectNotEnded extends ProjectUseCaseViolation {

        private ProjectNotEnded(ProjectBusinessRuleViolation violation) {
            super(violation);
        }
    }
}

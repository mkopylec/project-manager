package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.AggregateRepository;
import com.github.mkopylec.projectmanager.common.core.EventPublisher;

import java.util.List;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;

public abstract class ProjectRepository extends AggregateRepository<Project, ProjectId> {

    protected void save(Project aggregate, EventPublisher publisher) {
        super.save(aggregate, publisher, (e) -> {
            throw new ConcurrentProjectModification(aggregate.getId(), e);
        });
    }

    protected Project require(ProjectId projectId) {
        return find(projectId).orElseThrow(() -> new NoProjectExists(projectId));
    }

    protected abstract List<Project> findAll();

    static final class ConcurrentProjectModification extends ProjectBusinessRuleViolation {

        private ConcurrentProjectModification(ProjectId id, Exception cause) {
            super(properties("id", id), cause);
        }
    }

    static final class NoProjectExists extends ProjectBusinessRuleViolation {

        private NoProjectExists(ProjectId id) {
            super(properties("id", id));
        }
    }
}

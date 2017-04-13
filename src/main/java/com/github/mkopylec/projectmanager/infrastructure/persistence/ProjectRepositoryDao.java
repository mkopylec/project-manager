package com.github.mkopylec.projectmanager.infrastructure.persistence;

import java.util.List;

import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.project.ProjectRepository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
class ProjectRepositoryDao implements ProjectRepository {

    private static final String PROJECTS_COLLECTION = "projects";

    private MongoTemplate mongo;

    ProjectRepositoryDao(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public List<Project> findAll() {
        return mongo.findAll(Project.class, PROJECTS_COLLECTION);
    }

    @Override
    public void save(Project project) {
        mongo.save(project, PROJECTS_COLLECTION);
    }
}

package com.github.mkopylec.projectmanager.core.project;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.core.utils.Utilities.isEmpty;

@Repository
class ProjectRepository {

    private MongoOperations database;

    ProjectRepository(MongoOperations database) {
        this.database = database;
    }

    Project findByIdentifier(UUID identifier) {
        if (isEmpty(identifier)) {
            return null;
        }
        return database.findById(identifier, Project.class);
    }

    List<Project> findAll() {
        return database.findAll(Project.class);
    }

    void save(Project project) {
        database.save(project);
    }
}

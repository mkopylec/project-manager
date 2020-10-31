package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.OperationsDelayer;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;

@Repository
class ProjectRepository extends OperationsDelayer {

    private MongoOperations database;

    ProjectRepository(HttpServletRequest request, MongoOperations database) {
        super(request);
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
        addDelayedOperation(() -> database.save(project));
    }
}

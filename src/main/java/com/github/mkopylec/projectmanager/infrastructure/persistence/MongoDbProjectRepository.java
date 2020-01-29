package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;

/**
 * Secondary adapter
 */
@Repository
class MongoDbProjectRepository extends ProjectRepository {

    private MongoTemplate database;
    private ProjectPersistenceMapper mapper = new ProjectPersistenceMapper();

    MongoDbProjectRepository(MongoTemplate database) {
        this.database = database;
    }

    @Override
    protected Project findByIdentifier(String identifier) {
        if (isEmpty(identifier)) {
            return null;
        }
        ProjectDocument document = database.findById(identifier, ProjectDocument.class);
        return mapper.map(document);
    }

    @Override
    protected List<Project> findAll() {
        List<ProjectDocument> documents = database.findAll(ProjectDocument.class);
        return mapElements(documents, document -> mapper.map(document));
    }

    @Override
    protected void save(Project project) {
        ProjectDocument document = mapper.map(project);
        database.save(document);
    }
}

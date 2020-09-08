package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static java.lang.ThreadLocal.withInitial;

/**
 * Secondary adapter
 */
@Repository
class MongoDbProjectRepository extends ProjectRepository {

    private MongoTemplate database;
    private ProjectPersistenceMapper mapper = new ProjectPersistenceMapper();
    private ThreadLocal<List<Project>> projectsToSave = withInitial(ArrayList::new);

    MongoDbProjectRepository(MongoTemplate database) {
        this.database = database;
    }

    @Override
    protected Project findByIdentifier(String identifier) {
        if (isEmpty(identifier)) {
            return null;
        }
        var document = database.findById(identifier, ProjectDocument.class);
        return mapper.map(document);
    }

    @Override
    protected List<Project> findAll() {
        var documents = database.findAll(ProjectDocument.class);
        return mapElements(documents, document -> mapper.map(document));
    }

    @Override
    protected void save(Project project) {
        projectsToSave.get().add(project);
    }

    @Override
    public void commit() {
        projectsToSave.get().forEach(project -> {
            var document = mapper.map(project);
            database.save(document);
        });
    }

    @Override
    public void dispose() {
        projectsToSave.remove();
    }
}

package com.github.mkopylec.projectmanager.infrastructure.persistence;

import java.util.List;

import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static java.util.Collections.emptyList;

/**
 * Secondary adapter
 */
@Repository
class MongoDbProjectRepository extends MongoDbRepository implements ProjectRepository {

    MongoDbProjectRepository(MongoTemplate database, ModelMapper mapper) {
        super(database, mapper);
    }

    @Override
    public Project findByIdentifier(String identifier) {
        ProjectDocument document = getDatabase().findById(identifier, ProjectDocument.class);
        if (document == null) {
            return null;
        }
        return getMapper().map(document, Project.class);
    }

    @Override
    public List<Project> findAll() {
        List<ProjectDocument> documents = getDatabase().findAll(ProjectDocument.class);
        if (documents.isEmpty()) {
            return emptyList();
        }
        return getMapper().map(documents, new TypeToken<List<Project>>() {

        }.getType());
    }

    @Override
    public void save(Project project) {
        ProjectDocument document = getMapper().map(project, ProjectDocument.class);
        getDatabase().save(document);
    }
}

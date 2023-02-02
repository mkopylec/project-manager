package com.github.mkopylec.projectmanager.project.outbound.mongodb;

import com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation;
import com.github.mkopylec.projectmanager.project.core.Project;
import com.github.mkopylec.projectmanager.project.core.ProjectId;
import com.github.mkopylec.projectmanager.project.core.ProjectRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Repository
class MongoDbProjectRepository extends ProjectRepository {

    private final MongoOperations mongoDb;

    MongoDbProjectRepository(MongoOperations mongoDb) {
        this.mongoDb = mongoDb;
    }

    @Override
    protected void save(Project aggregate, Function<Exception, ? extends BusinessRuleViolation> onConcurrentModification) {
        var document = new ProjectDocument(aggregate);
        try {
            mongoDb.save(document);
        } catch (DuplicateKeyException | OptimisticLockingFailureException e) {
            throw onConcurrentModification.apply(e);
        }
    }

    @Override
    protected Optional<Project> find(ProjectId projectId) {
        var document = mongoDb.findById(projectId.getValue(), ProjectDocument.class);
        return ofNullable(document).map(ProjectDocument::toProject);
    }

    @Override
    protected List<Project> findAll() {
        var documents = mongoDb.findAll(ProjectDocument.class);
        return documents.stream().map(ProjectDocument::toProject).toList();
    }
}

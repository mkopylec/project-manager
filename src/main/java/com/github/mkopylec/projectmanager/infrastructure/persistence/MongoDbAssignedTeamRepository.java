package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.project.AssignedTeam;
import com.github.mkopylec.projectmanager.core.project.AssignedTeamRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;

/**
 * Secondary adapter
 */
@Repository
class MongoDbAssignedTeamRepository extends AssignedTeamRepository {

    private MongoTemplate database;
    private AssignedTeamPersistenceMapper mapper = new AssignedTeamPersistenceMapper();

    MongoDbAssignedTeamRepository(MongoTemplate database) {
        this.database = database;
    }

    @Override
    protected AssignedTeam findByName(String name) {
        if (isEmpty(name)) {
            return null;
        }
        var document = database.findById(name, TeamDocument.class);
        return mapper.map(document);
    }

    @Override
    protected void save(AssignedTeam team) {
        database.updateFirst(mapper.mapToQuery(team), mapper.mapToUpdate(team), TeamDocument.class);
    }
}

package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.team.Team;
import com.github.mkopylec.projectmanager.core.team.TeamRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;

/**
 * Secondary adapter
 */
@Repository
class MongoDbTeamRepository extends TeamRepository {

    private MongoTemplate database;
    private TeamPersistenceMapper mapper = new TeamPersistenceMapper();

    MongoDbTeamRepository(MongoTemplate database) {
        this.database = database;
    }

    @Override
    protected Team findByName(String name) {
        if (isEmpty(name)) {
            return null;
        }
        var document = database.findById(name, TeamDocument.class);
        return mapper.map(document);
    }

    @Override
    protected List<Team> findAll() {
        var documents = database.findAll(TeamDocument.class);
        return mapElements(documents, document -> mapper.map(document));
    }

    @Override
    protected void save(Team team) {
        var document = mapper.map(team);
        database.save(document);
    }
}

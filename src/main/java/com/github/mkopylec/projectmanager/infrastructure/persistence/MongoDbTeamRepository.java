package com.github.mkopylec.projectmanager.infrastructure.persistence;

import java.util.List;

import com.github.mkopylec.projectmanager.core.team.Team;
import com.github.mkopylec.projectmanager.core.team.TeamRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static com.github.mkopylec.projectmanager.infrastructure.persistence.TeamDocument.TEAMS_COLLECTION;
import static java.util.Collections.emptyList;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Secondary adapter
 */
@Repository
class MongoDbTeamRepository extends MongoDbRepository implements TeamRepository {

    MongoDbTeamRepository(MongoTemplate database, ModelMapper mapper) {
        super(database, mapper);
    }

    @Override
    public boolean existsByName(String name) {
        return getDatabase().exists(query(where("_id").is(name)), TEAMS_COLLECTION);
    }

    @Override
    public Team findByName(String name) {
        TeamDocument document = getDatabase().findById(name, TeamDocument.class);
        if (document == null) {
            return null;
        }
        return getMapper().map(document, Team.class);
    }

    @Override
    public List<Team> findAll() {
        List<TeamDocument> documents = getDatabase().findAll(TeamDocument.class);
        if (documents.isEmpty()) {
            return emptyList();
        }
        return getMapper().map(documents, new TypeToken<List<Team>>() {

        }.getType());
    }

    @Override
    public void save(Team team) {
        if (team != null) {
            TeamDocument document = getMapper().map(team, TeamDocument.class);
            getDatabase().save(document);
        }
    }
}

package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
class TeamDao implements TeamRepository {

    private static final String TEAMS_COLLECTION = "teams";

    private MongoTemplate mongo;

    TeamDao(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public boolean existsByName(String name) {
        return mongo.exists(query(where("_id").is(name)), TEAMS_COLLECTION);
    }

    @Override
    public void save(Team team) {
        mongo.save(team, TEAMS_COLLECTION);
    }
}

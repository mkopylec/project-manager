package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
class TeamDao implements TeamRepository {

    private final MongoTemplate mongo;

    TeamDao(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void save(Team team) {
        mongo.save(team, "teams");
    }
}

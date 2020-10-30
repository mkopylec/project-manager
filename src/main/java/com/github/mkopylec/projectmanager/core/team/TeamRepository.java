package com.github.mkopylec.projectmanager.core.team;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.utils.Utilities.isEmpty;

@Repository
class TeamRepository {

    private MongoOperations database;

    TeamRepository(MongoOperations database) {
        this.database = database;
    }

    Team findByName(String name) {
        if (isEmpty(name)) {
            return null;
        }
        return database.findById(name, Team.class);
    }

    List<Team> findAll() {
        return database.findAll(Team.class);
    }

    void save(Team team) {
        database.save(team);
    }
}

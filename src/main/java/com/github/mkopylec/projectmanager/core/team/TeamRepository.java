package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.UnitOfWork;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;

@Repository
class TeamRepository extends UnitOfWork {

    private MongoOperations database;

    TeamRepository(HttpServletRequest request, MongoOperations database) {
        super(request);
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
        addWriteOperation(() -> database.save(team));
    }
}

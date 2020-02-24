package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.project.AssignedTeam;
import com.github.mkopylec.projectmanager.core.project.AssignedTeam.AssignedTeamPersistenceHelper;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

class AssignedTeamPersistenceMapper {

    private AssignedTeamPersistenceHelper teamHelper = new AssignedTeamPersistenceHelper();

    Query mapToQuery(AssignedTeam team) {
        return query(where("name").is(teamHelper.getName(team)));
    }

    Update mapToUpdate(AssignedTeam team) {
        return update("currentlyImplementedProjects", teamHelper.getCurrentlyImplementedProjects(team));
    }

    AssignedTeam map(TeamDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        return teamHelper.createTeam(document.getName(), document.getCurrentlyImplementedProjects());
    }
}

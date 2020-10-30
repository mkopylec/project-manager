package com.github.mkopylec.projectmanager.utils;

import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.team.Team;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.mkopylec.projectmanager.utils.Exactly.exactly;
import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Every class that uses Mockito API must be in Java cause Groovy cannot handle the API properly!
@Component
public class MongoDb {

    private MongoOperations mongo;

    public MongoDb(MongoOperations mongo) {
        this.mongo = mongo;
    }

    public void stubFindingById(ProjectBuilder project) {
        when(mongo.findById(project.getIdentifier(), Project.class)).thenReturn(project.build());
    }

    public void stubFindingAllProjects(List<ProjectBuilder> projects) {
        when(mongo.findAll(Project.class)).thenReturn(projects.stream().map(ProjectBuilder::build).collect(toList()));
    }

    public void stubFindingById(TeamBuilder team) {
        when(mongo.findById(team.getName(), Team.class)).thenReturn(team.build());
    }

    public void stubFindingAllTeams(List<TeamBuilder> teams) {
        when(mongo.findAll(Team.class)).thenReturn(teams.stream().map(TeamBuilder::build).collect(toList()));
    }

    public void expectInserted(ProjectBuilder project) {
        verify(mongo).save(exactly(project.build(), "identifier"));
    }

    public void expectSaved(TeamBuilder team) {
        verify(mongo).save(exactly(team.build()));
    }

    public void expectNotSaved(Class<?> type) {
        verify(mongo, never()).save(any(type));
    }

    public void expectUpdated(ProjectBuilder project) {
        verify(mongo).save(exactly(project.build()));
    }
}

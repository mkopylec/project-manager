package com.github.mkopylec.projectmanager.configuration;

import com.github.mkopylec.projectmanager.core.project.AssignedTeamRepository;
import com.github.mkopylec.projectmanager.core.project.EventPublisher;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.project.UniqueIdentifierGenerator;
import com.github.mkopylec.projectmanager.core.team.TeamRepository;
import com.github.mkopylec.projectmanager.core.team.TeamService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProjectManagerConfiguration {

    @Bean
    ProjectService projectService(
            UniqueIdentifierGenerator identifierGenerator,
            ProjectRepository projectRepository,
            AssignedTeamRepository teamRepository,
            EventPublisher eventPublisher
    ) {
        return new ProjectService(identifierGenerator, projectRepository, teamRepository, eventPublisher);
    }

    @Bean
    TeamService teamService(
            TeamRepository teamRepository
    ) {
        return new TeamService(teamRepository);
    }
}

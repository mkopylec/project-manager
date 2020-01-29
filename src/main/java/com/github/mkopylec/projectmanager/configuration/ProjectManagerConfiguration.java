package com.github.mkopylec.projectmanager.configuration;

import com.github.mkopylec.projectmanager.core.ProjectManager;
import com.github.mkopylec.projectmanager.core.common.EventPublisher;
import com.github.mkopylec.projectmanager.core.project.ProjectRepository;
import com.github.mkopylec.projectmanager.core.project.UniqueIdentifierGenerator;
import com.github.mkopylec.projectmanager.core.team.TeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProjectManagerConfiguration {

    @Bean
    ProjectManager projectManager(UniqueIdentifierGenerator identifierGenerator, ProjectRepository projectRepository, EventPublisher eventPublisher, TeamRepository teamRepository) {
        return new ProjectManager(identifierGenerator, projectRepository, eventPublisher, teamRepository);
    }
}

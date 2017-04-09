package com.github.mkopylec.projectmanager.domain.project;

import com.github.mkopylec.projectmanager.domain.services.UniqueIdentifierGenerator;

import org.springframework.stereotype.Service;

import static com.github.mkopylec.projectmanager.domain.project.Status.TO_DO;

@Service
public class ProjectFactory {

    private final UniqueIdentifierGenerator identifierGenerator;

    public ProjectFactory(UniqueIdentifierGenerator identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
    }

    public Project createProjectDraft(String name) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name, TO_DO);
    }

    public Project createFullProject(String name) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name, TO_DO);
    }
}

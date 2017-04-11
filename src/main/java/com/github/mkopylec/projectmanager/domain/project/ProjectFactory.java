package com.github.mkopylec.projectmanager.domain.project;

import com.github.mkopylec.projectmanager.domain.services.UniqueIdentifierGenerator;

import org.springframework.stereotype.Component;

@Component
public class ProjectFactory {

    private UniqueIdentifierGenerator identifierGenerator;

    public ProjectFactory(UniqueIdentifierGenerator identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
    }

    public Project createProjectDraft(String name) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name);
    }

    public Project createFullProject(String name) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name, TO_DO);
    }
}

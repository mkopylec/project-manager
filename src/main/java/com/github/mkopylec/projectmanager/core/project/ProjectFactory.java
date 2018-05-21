package com.github.mkopylec.projectmanager.core.project;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ProjectFactory {

    private UniqueIdentifierGenerator identifierGenerator;

    ProjectFactory(UniqueIdentifierGenerator identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
    }

    Project createProjectDraft(String name) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name);
    }

    Project createFullProject(String name, List<Feature> features) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name, features);
    }
}

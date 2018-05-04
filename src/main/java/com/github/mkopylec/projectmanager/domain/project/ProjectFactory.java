package com.github.mkopylec.projectmanager.domain.project;

import com.github.mkopylec.projectmanager.domain.services.UniqueIdentifierGenerator;
import com.github.mkopylec.projectmanager.domain.values.Feature;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public Project createFullProject(String name, List<Feature> features) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name, features);
    }
}

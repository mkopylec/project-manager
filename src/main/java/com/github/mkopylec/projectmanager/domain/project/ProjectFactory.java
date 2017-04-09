package com.github.mkopylec.projectmanager.domain.project;

import com.github.mkopylec.projectmanager.domain.services.UniqueIdentifierGenerator;

import org.springframework.stereotype.Component;

import static com.github.mkopylec.projectmanager.domain.values.Status.TO_DO;

@Component
public class ProjectFactory {

    private final UniqueIdentifierGenerator identifierGenerator;

    public ProjectFactory(UniqueIdentifierGenerator identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
    }

    public Project createProjectDraft(String name) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return new Project(identifier, name, TO_DO);
    }
}

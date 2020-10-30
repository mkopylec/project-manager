package com.github.mkopylec.projectmanager.utils

import com.github.mkopylec.projectmanager.core.project.Feature
import com.github.mkopylec.projectmanager.core.project.Project
import com.github.mkopylec.projectmanager.core.project.Requirement
import com.github.mkopylec.projectmanager.core.project.Status

import static java.util.UUID.randomUUID

class ProjectBuilder {

    UUID identifier = randomUUID()
    String name
    Status status
    String assignedTeam
    List<FeatureBuilder> features

    protected Project build() {
        new Project(identifier, name, status, assignedTeam, features.collect { it.build() })
    }
}

class FeatureBuilder {

    String name
    Status status
    Requirement requirement

    protected Feature build() {
        new Feature(name, status, requirement)
    }
}

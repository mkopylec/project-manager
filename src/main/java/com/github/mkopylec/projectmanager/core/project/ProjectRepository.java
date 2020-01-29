package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

/**
 * Secondary port
 */
public abstract class ProjectRepository {

    protected abstract Project findByIdentifier(String identifier);

    protected abstract List<Project> findAll();

    protected abstract void save(Project project);
}

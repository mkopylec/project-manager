package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.Committable;

import java.util.List;

/**
 * Secondary port
 */
public abstract class ProjectRepository implements Committable {

    protected abstract Project findByIdentifier(String identifier);

    protected abstract List<Project> findAll();

    protected abstract void save(Project project);
}

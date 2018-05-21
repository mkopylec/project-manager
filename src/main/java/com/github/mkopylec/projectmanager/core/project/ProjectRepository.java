package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

/**
 * Secondary port
 */
public interface ProjectRepository {

    Project findByIdentifier(String identifier);

    List<Project> findAll();

    void save(Project project);
}

package com.github.mkopylec.projectmanager.domain.project;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAll();

    void save(Project project);
}

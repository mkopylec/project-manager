package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.application.ProjectService;
import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.NewProject;
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/projects")
class ProjectController {

    private ProjectService projectService;

    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/drafts")
    public void createProject(@RequestBody NewProjectDraft newProjectDraft) {
        projectService.createProject(newProjectDraft);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public void createProject(@RequestBody NewProject newProject) {
        projectService.createProject(newProject);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<ExistingProjectDraft> getProjects() {
        return projectService.getProjects();
    }
}

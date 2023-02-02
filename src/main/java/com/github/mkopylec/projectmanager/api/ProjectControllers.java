package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.application.ProjectServices;
import com.github.mkopylec.projectmanager.application.dto.ExistingProject;
import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.NewProject;
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.application.dto.UpdatedProject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/projects")
class ProjectControllers {

    private ProjectServices projectService;

    ProjectControllers(ProjectServices projectService) {
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

    @ResponseStatus(OK)
    @GetMapping("/{projectIdentifier}")
    public ExistingProject getProject(@PathVariable String projectIdentifier) {
        return projectService.getProject(projectIdentifier);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping("/{projectIdentifier}")
    public void updateProject(@PathVariable String projectIdentifier, @RequestBody UpdatedProject updatedProject) {
        projectService.updateProject(projectIdentifier, updatedProject);
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/{projectIdentifier}/started")
    public void startProject(@PathVariable String projectIdentifier) {
        projectService.startProject(projectIdentifier);
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/{projectIdentifier}/ended")
    public void endProject(@PathVariable String projectIdentifier, @RequestBody ProjectEndingCondition endingCondition) {
        projectService.endProject(projectIdentifier, endingCondition);
    }
}

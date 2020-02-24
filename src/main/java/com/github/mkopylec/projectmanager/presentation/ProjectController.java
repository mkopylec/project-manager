package com.github.mkopylec.projectmanager.presentation;

import com.github.mkopylec.projectmanager.core.project.ProjectService;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.NewProject;
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProject;
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

/**
 * Primary adapter
 */
@RestController
@RequestMapping("/projects")
class ProjectController {

    private ProjectService projectService;

    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/drafts")
    void createProject(@RequestBody NewProjectDraft newProjectDraft) {
        projectService.createProject(newProjectDraft);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void createProject(@RequestBody NewProject newProject) {
        projectService.createProject(newProject);
    }

    @ResponseStatus(OK)
    @GetMapping
    List<ExistingProjectDraft> getProjects() {
        return projectService.getProjects();
    }

    @ResponseStatus(OK)
    @GetMapping("/{projectIdentifier}")
    ExistingProject getProject(@PathVariable String projectIdentifier) {
        return projectService.getProject(projectIdentifier);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping("/{projectIdentifier}")
    void updateProject(@PathVariable String projectIdentifier, @RequestBody UpdatedProject updatedProject) {
        projectService.updateProject(new UpdatedProject(projectIdentifier, updatedProject.getName(), updatedProject.getTeam(), updatedProject.getFeatures()));
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/{projectIdentifier}/started")
    void startProject(@PathVariable String projectIdentifier) {
        projectService.startProject(projectIdentifier);
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/{projectIdentifier}/ended")
    void endProject(@PathVariable String projectIdentifier, @RequestBody ProjectEndingCondition endingCondition) {
        projectService.endProject(new ProjectEndingCondition(projectIdentifier, endingCondition.isOnlyNecessaryFeatureDone()));
    }
}

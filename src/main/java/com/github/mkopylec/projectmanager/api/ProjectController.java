package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.api.dto.ExistingProject;
import com.github.mkopylec.projectmanager.api.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.NewProject;
import com.github.mkopylec.projectmanager.api.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.ProjectEndingCondition;
import com.github.mkopylec.projectmanager.api.dto.UpdatedProject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.UUID.fromString;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/projects")
class ProjectController {

    private ProjectManager projectManager;

    ProjectController(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/drafts")
    void createProject(@RequestBody @Valid NewProjectDraft newProjectDraft) {
        projectManager.createProject(newProjectDraft);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void createProject(@RequestBody @Valid NewProject newProject) {
        projectManager.createProject(newProject);
    }

    @ResponseStatus(OK)
    @GetMapping
    List<ExistingProjectDraft> getProjects() {
        return projectManager.getProjects();
    }

    @ResponseStatus(OK)
    @GetMapping("/{projectIdentifier}")
    ExistingProject getProject(@PathVariable String projectIdentifier) {
        return projectManager.getProject(fromString(projectIdentifier));
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping("/{projectIdentifier}")
    void updateProject(@PathVariable String projectIdentifier, @RequestBody @Valid UpdatedProject updatedProject) {
        projectManager.updateProject(fromString(projectIdentifier), updatedProject);
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/{projectIdentifier}/started")
    void startProject(@PathVariable String projectIdentifier) {
        projectManager.startProject(fromString(projectIdentifier));
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/{projectIdentifier}/ended")
    void endProject(@PathVariable String projectIdentifier, @RequestBody ProjectEndingCondition endingCondition) {
        projectManager.endProject(fromString(projectIdentifier), endingCondition);
    }
}

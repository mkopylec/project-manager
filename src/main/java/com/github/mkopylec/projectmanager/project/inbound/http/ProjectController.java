package com.github.mkopylec.projectmanager.project.inbound.http;

import com.github.mkopylec.projectmanager.project.core.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.github.mkopylec.projectmanager.project.core.IncomingDto.ProjectId;
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectDraftBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.ProjectEndingBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedProjectBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.ExistingProjectBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.ExistingProjectDraftBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.ExistingProjectDraftsBody;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/projects", produces = APPLICATION_JSON_VALUE)
class ProjectController {

    private final ProjectService service;

    private ProjectController(ProjectService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/drafts", consumes = APPLICATION_JSON_VALUE)
    void createProjectDraft(@RequestBody NewProjectDraftBody body) {
        service.createProjectDraft(body.toNewProjectDraft());
    }

    @ResponseStatus(OK)
    @GetMapping(path = "/drafts")
    ExistingProjectDraftsBody loadProjectDrafts() {
        var projectDrafts = service.loadProjectDrafts().stream().map(ExistingProjectDraftBody::new).toList();
        return new ExistingProjectDraftsBody(projectDrafts);
    }

    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    void createProject(@RequestBody NewProjectBody body) {
        service.createProject(body.toNewProject());
    }

    @ResponseStatus(OK)
    @GetMapping(path = "/{projectId}")
    ExistingProjectBody loadProject(@PathVariable UUID projectId) {
        var existingProject = service.loadProject(new ProjectId(projectId));
        return new ExistingProjectBody(existingProject);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(path = "/{projectId}", consumes = APPLICATION_JSON_VALUE)
    void updateProject(@PathVariable UUID projectId, @RequestBody UpdatedProjectBody body) {
        service.updateProject(body.toUpdatedProject(projectId));
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{projectId}/started")
    void startProject(@PathVariable UUID projectId) {
        service.startProject(new ProjectId(projectId));
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{projectId}/ended", consumes = APPLICATION_JSON_VALUE)
    void endProject(@PathVariable UUID projectId, @RequestBody ProjectEndingBody body) {
        service.endProject(body.toProjectEnding(projectId));
    }
}

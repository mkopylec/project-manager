package com.github.mkopylec.projectmanager.project

import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.CompletionStatusBody.IN_PROGRESS
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.NO_PROJECT_EXISTS
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.NO_TEAM_ASSIGNED_TO_PROJECT
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.PROJECT_ALREADY_STARTED
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_NEW_FEATURES
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_UPDATED_FEATURES
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.NONEXISTENT_PROJECT_ID
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.PROJECT_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.TEAM_NAME
import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class ProjectStartSpecification extends ProjectSpecification {

    def "Should start a project"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedProject = new UpdatedProjectBody(newProject.name(), TEAM_NAME, EMPTY_UPDATED_FEATURES)
        projects.updateProject(projectId, updatedProject)

        when:
        def response = projects.startProject(projectId)

        then:
        with(response) {
            status == NO_CONTENT
        }

        when:
        response = projects.loadProject(projectId)

        then:
        with(response) {
            status == OK
            with(body) {
                status() == IN_PROGRESS
            }
        }
    }

    def "Should not start an already started project"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedProject = new UpdatedProjectBody(newProject.name(), TEAM_NAME, EMPTY_UPDATED_FEATURES)
        projects.updateProject(projectId, updatedProject)
        projects.startProject(projectId)

        when:
        def response = projects.startProject(projectId)

        then:
        with(response) {
            status == CONFLICT
            with(failure) {
                code == PROJECT_ALREADY_STARTED
            }
        }
    }

    def "Should not start a project when no team is assigned to it"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()

        when:
        def response = projects.startProject(projectId)

        then:
        with(response) {
            status == CONFLICT
            with(failure) {
                code == NO_TEAM_ASSIGNED_TO_PROJECT
            }
        }
    }

    def "Should not start a nonexistent project"() {
        when:
        def response = projects.startProject(NONEXISTENT_PROJECT_ID)

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                code == NO_PROJECT_EXISTS
            }
        }
    }
}

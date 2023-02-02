package com.github.mkopylec.projectmanager.project

import com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies

import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.NECESSARY
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.OPTIONAL
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.RECOMMENDED
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewFeatureBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectDraftBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.CompletionStatusBody.TO_DO
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.INVALID_FEATURE_NAME
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.INVALID_PROJECT_NAME
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.NO_PROJECT_EXISTS
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_NEW_FEATURES
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.FEATURE_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.NONEXISTENT_PROJECT_ID
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.PROJECT_NAME
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class ProjectCreationAndLoadingSpecification extends ProjectSpecification {

    def "Should create a new project draft and browse it"() {
        given:
        def newProjectDraft = new NewProjectDraftBody(PROJECT_NAME)

        when:
        def response = projects.createProjectDraft(newProjectDraft)

        then:
        with(response) {
            status == CREATED
        }

        when:
        response = projects.loadProjectDrafts()

        then:
        with(response) {
            status == OK
            with(body) {
                projectDrafts() != null
                projectDrafts().size() == 1
                with(projectDrafts()[0]) {
                    id() != null
                    name() == newProjectDraft.name()
                }
            }
        }

        and:
        def projectId = response.body.projectDrafts()[0].id()

        when:
        response = projects.loadProject(projectId)

        then:
        with(response) {
            status == OK
            with(body) {
                id() == projectId
                name() == newProjectDraft.name()
                status() == TO_DO
                team() == null
                features() == []
            }
        }
    }

    def "Should not create a new project draft with invalid name"() {
        given:
        def newProjectDraft = new NewProjectDraftBody(name)

        when:
        def response = projects.createProjectDraft(newProjectDraft)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                code == INVALID_PROJECT_NAME
            }
        }

        where:
        name << [null, '', '  ']
    }

    def "Should create a new full project with a feature and browse it"() {
        given:
        def newFeature = new NewFeatureBody(FEATURE_NAME, requestedRequirement)
        def newProject = new NewProjectBody(PROJECT_NAME, [newFeature])

        when:
        def response = projects.createProject(newProject)

        then:
        with(response) {
            status == CREATED
        }

        when:
        response = projects.loadProjectDrafts()

        then:
        with(response) {
            status == OK
            with(body) {
                projectDrafts() != null
                projectDrafts().size() == 1
                with(projectDrafts()[0]) {
                    id() != null
                    name() == newProject.name()
                }
            }
        }

        and:
        def projectId = response.body.projectDrafts()[0].id()

        when:
        response = projects.loadProject(projectId)

        then:
        with(response) {
            status == OK
            with(body) {
                id() == projectId
                name() == newProject.name()
                team() == null
                features() != null
                features().size() == 1
                with(features()[0]) {
                    name() == newFeature.name()
                    status() == TO_DO
                    requirement() == resultedRequirement
                }
            }
        }

        where:
        requestedRequirement | resultedRequirement
        OPTIONAL             | ResponseBodies.FeatureRequirementBody.OPTIONAL
        RECOMMENDED          | ResponseBodies.FeatureRequirementBody.RECOMMENDED
        NECESSARY            | ResponseBodies.FeatureRequirementBody.NECESSARY
    }

    def "Should not create a new full project with an invalid name"() {
        given:
        def newProject = new NewProjectBody(name, EMPTY_NEW_FEATURES)

        when:
        def response = projects.createProject(newProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                code == INVALID_PROJECT_NAME
            }
        }

        where:
        name << [null, '', '  ']
    }

    def "Should not create a new full project with a feature with an invalid name"() {
        given:
        def newFeature = new NewFeatureBody(name, NECESSARY)
        def newProject = new NewProjectBody(PROJECT_NAME, [newFeature])

        when:
        def response = projects.createProject(newProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                code == INVALID_FEATURE_NAME
            }
        }

        where:
        name << [null, '', '  ']
    }

    def "Should browse projects if none exists"() {
        when:
        def response = projects.loadProjectDrafts()

        then:
        with(response) {
            status == OK
            with(body) {
                projectDrafts() == []
            }
        }
    }

    def "Should not browse a project if it does not exist"() {
        when:
        def response = projects.loadProject(NONEXISTENT_PROJECT_ID)

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                code == NO_PROJECT_EXISTS
            }
        }
    }
}

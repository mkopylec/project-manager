package com.github.mkopylec.projectmanager.project

import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.CompletionStatusBody.DONE
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.CompletionStatusBody.IN_PROGRESS
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.CompletionStatusBody.TO_DO
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.NECESSARY
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.OPTIONAL
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.RECOMMENDED
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewFeatureBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedFeatureBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.CompletionStatusBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.INVALID_FEATURE_NAME
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.INVALID_PROJECT_NAME
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FeatureRequirementBody
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_NEW_FEATURES
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_TEAM_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_UPDATED_FEATURES
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.FEATURE_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.OTHER_FEATURE_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.OTHER_PROJECT_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.PROJECT_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.TEAM_NAME
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class ProjectUpdateSpecification extends ProjectSpecification {

    def "Should update a project by setting a feature with a status and browse it"() {
        given:
        def newFeature = new NewFeatureBody(FEATURE_NAME, requestedFeatureRequirement)
        def newProject = new NewProjectBody(PROJECT_NAME, [newFeature])
        projects.createProject(newProject)
        def projId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedFeature = new UpdatedFeatureBody(OTHER_FEATURE_NAME, requestedFeatureStatus, requestedFeatureRequirement)
        def updatedProject = new UpdatedProjectBody(OTHER_PROJECT_NAME, TEAM_NAME, [updatedFeature])
        clock.stop()

        when:
        def response = projects.updateProject(projId, updatedProject)

        then:
        with(response) {
            status == NO_CONTENT
        }
        with(publishedEvents.getProjectTeamAssigned()) {
            eventId != null
            occurrenceDate == clock.instant()
            projectId == projId
            assignedTeam == updatedProject.team()
        }

        and:
        clock.resume()

        when:
        response = projects.loadProject(projId)

        then:
        with(response) {
            status == OK
            with(body) {
                id() == projId
                name() == updatedProject.name()
                team() == updatedProject.team()
                features() != null
                features().size() == 1
                with(features()[0]) {
                    name() == updatedFeature.name()
                    status() == resultedFeatureStatus
                    requirement() == resultedFeatureRequirement
                }
            }
        }

// TODO in team module tests when:
//        response = get('/teams', new ParameterizedTypeReference<List<ExistingTeam>>() {})
//
//        then:
//        response.statusCode == OK
//        response.body != null
//        response.body.size() == 1
//        with(response.body[0]) {
//            name == 'Team 2'
//            currentlyImplementedProjects == 1
//            !busy
//            members == []
//        }

        where:
        requestedFeatureStatus | requestedFeatureRequirement | resultedFeatureStatus            | resultedFeatureRequirement
        TO_DO                  | OPTIONAL                    | CompletionStatusBody.TO_DO       | FeatureRequirementBody.OPTIONAL
        IN_PROGRESS            | RECOMMENDED                 | CompletionStatusBody.IN_PROGRESS | FeatureRequirementBody.RECOMMENDED
        DONE                   | NECESSARY                   | CompletionStatusBody.DONE        | FeatureRequirementBody.NECESSARY
    }

    def "Should not update a project with an invalid name"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedProject = new UpdatedProjectBody(name, EMPTY_TEAM_NAME, EMPTY_UPDATED_FEATURES)

        when:
        def response = projects.updateProject(projectId, updatedProject)

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

    def "Should not update a project with a feature with an invalid name"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedFeature = new UpdatedFeatureBody(name, IN_PROGRESS, OPTIONAL)
        def updatedProject = new UpdatedProjectBody(newProject.name(), EMPTY_TEAM_NAME, [updatedFeature])

        when:
        def response = projects.updateProject(projectId, updatedProject)

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
}

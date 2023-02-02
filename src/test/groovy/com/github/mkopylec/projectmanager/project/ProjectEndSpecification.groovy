package com.github.mkopylec.projectmanager.project

import com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies

import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.CompletionStatusBody.DONE
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.CompletionStatusBody.IN_PROGRESS
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.CompletionStatusBody.TO_DO
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.NECESSARY
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.FeatureRequirementBody.OPTIONAL
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.ProjectEndingBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.ProjectEndingConditionBody.ALL_FEATURES_DONE
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.ProjectEndingConditionBody.ONLY_NECESSARY_FEATURES_DONE
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedFeatureBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.INVALID_PROJECT_FEATURES_STATE
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.NO_PROJECT_EXISTS
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.PROJECT_NOT_STARTED
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_NEW_FEATURES
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.EMPTY_UPDATED_FEATURES
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.FEATURE_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.NONEXISTENT_PROJECT_ID
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.PROJECT_NAME
import static com.github.mkopylec.projectmanager.project.utils.values.SampleValues.TEAM_NAME
import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class ProjectEndSpecification extends ProjectSpecification {

    def "Should end a project when the ending conditions are fulfilled"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedProject = new UpdatedProjectBody(newProject.name(), TEAM_NAME, features)
        projects.updateProject(projId, updatedProject)
        projects.startProject(projId)
        def projectEnding = new ProjectEndingBody(projectEndingCondition)
        clock.stop()

        when:
        def response = projects.endProject(projId, projectEnding)

        then:
        with(response) {
            status == NO_CONTENT
        }
        with(publishedEvents.getProjectEnded()) {
            eventId != null
            occurrenceDate == clock.instant()
            projectId == projId
        }

        and:
        clock.resume()

        when:
        response = projects.loadProject(projId)

        then:
        with(response) {
            status == OK
            with(body) {
                status() == ResponseBodies.CompletionStatusBody.DONE
            }
        }

// TODO in team module tests
//  with(get('/teams', new ParameterizedTypeReference<List<ExistingTeam>>() {}).body[0]) {
//            name == 'Team 1'
//            currentlyImplementedProjects == 0
//        }

        where:
        features                                                      | projectEndingCondition
        []                                                            | ONLY_NECESSARY_FEATURES_DONE
        []                                                            | ALL_FEATURES_DONE
        [new UpdatedFeatureBody(FEATURE_NAME, DONE, NECESSARY)]       | ONLY_NECESSARY_FEATURES_DONE
        [new UpdatedFeatureBody(FEATURE_NAME, IN_PROGRESS, OPTIONAL)] | ONLY_NECESSARY_FEATURES_DONE
        [new UpdatedFeatureBody(FEATURE_NAME, DONE, NECESSARY)]       | ALL_FEATURES_DONE
    }

    def "Should not end a project when the ending conditions are not fulfilled"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedProject = new UpdatedProjectBody(newProject.name(), TEAM_NAME, features)
        projects.updateProject(projectId, updatedProject)
        projects.startProject(projectId)
        def projectEnding = new ProjectEndingBody(projectEndingCondition)

        when:
        def response = projects.endProject(projectId, projectEnding)

        then:
        with(response) {
            status == CONFLICT
            with(failure) {
                code == INVALID_PROJECT_FEATURES_STATE
            }
        }

        where:
        features                                                      | projectEndingCondition
        [new UpdatedFeatureBody(FEATURE_NAME, TO_DO, NECESSARY)]      | ONLY_NECESSARY_FEATURES_DONE
        [new UpdatedFeatureBody(FEATURE_NAME, IN_PROGRESS, OPTIONAL)] | ALL_FEATURES_DONE
    }

    def "Should not end a not started project"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def projectEnding = new ProjectEndingBody(ALL_FEATURES_DONE)

        when:
        def response = projects.endProject(projectId, projectEnding)

        then:
        with(response) {
            status == CONFLICT
            with(failure) {
                code == PROJECT_NOT_STARTED
            }
        }
    }

    def "Should not end an already ended project"() {
        given:
        def newProject = new NewProjectBody(PROJECT_NAME, EMPTY_NEW_FEATURES)
        projects.createProject(newProject)
        def projectId = projects.loadProjectDrafts().body.projectDrafts()[0].id()
        def updatedProject = new UpdatedProjectBody(newProject.name(), TEAM_NAME, EMPTY_UPDATED_FEATURES)
        projects.updateProject(projectId, updatedProject)
        projects.startProject(projectId)
        def projectEnding = new ProjectEndingBody(ALL_FEATURES_DONE)
        projects.endProject(projectId, projectEnding)

        when:
        def response = projects.endProject(projectId, projectEnding)

        then:
        with(response) {
            status == CONFLICT
            with(failure) {
                code == PROJECT_NOT_STARTED
            }
        }
    }

    def "Should not end a nonexistent project"() {
        given:
        def projectEnding = new ProjectEndingBody(ALL_FEATURES_DONE)

        when:
        def response = projects.endProject(NONEXISTENT_PROJECT_ID, projectEnding)

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                code == NO_PROJECT_EXISTS
            }
        }
    }
}

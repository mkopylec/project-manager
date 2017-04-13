package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpecification
import com.github.mkopylec.projectmanager.application.dto.ExistingProject
import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft
import com.github.mkopylec.projectmanager.application.dto.NewFeature
import com.github.mkopylec.projectmanager.application.dto.NewProject
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft
import com.github.mkopylec.projectmanager.application.dto.ProjectFeature
import com.github.mkopylec.projectmanager.application.dto.UpdatedProject
import org.springframework.core.ParameterizedTypeReference
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class ProjectSpecification extends BasicSpecification {

    def "Should create new project draft and browse it"() {
        given:
        def projectDraft = new NewProjectDraft(name: 'Project 1')

        when:
        def response = post('/projects/drafts', projectDraft)

        then:
        response.statusCode == CREATED

        when:
        response = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {})

        then:
        response.statusCode == OK
        response.body != null
        response.body.size() == 1
        with(response.body[0]) {
            identifier != null
            name == 'Project 1'
        }

        and:
        def projectIdentifier = response.body[0].identifier

        when:
        response = get("/projects/$projectIdentifier", ExistingProject)

        then:
        response.statusCode == OK
        response.body != null
        with(response.body) {
            identifier == projectIdentifier
            name == 'Project 1'
            status == 'TO_DO'
        }
    }

    @Unroll
    def "Should not create an unnamed new project draft"() {
        given:
        def projectDraft = new NewProjectDraft(name: name)

        when:
        def response = post('/projects/drafts', projectDraft)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_PROJECT_NAME'

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should create new full project with a #requirement feature and browse it"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: requirement)
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == CREATED

        when:
        response = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {})

        then:
        response.statusCode == OK
        response.body != null
        response.body.size() == 1
        with(response.body[0]) {
            identifier != null
            name == 'Project 1'
        }

        and:
        def projectIdentifier = response.body[0].identifier

        when:
        response = get("/projects/$projectIdentifier", ExistingProject)

        then:
        response.statusCode == OK
        response.body != null
        with(response.body) {
            identifier == projectIdentifier
            name == 'Project 1'
            status == 'TO_DO'
            features != null
            features.size() == 1
            features[0].name == 'Feature 1'
            features[0].status == 'TO_DO'
            features[0].requirement == requirement
        }

        where:
        requirement << ['OPTIONAL', 'RECOMMENDED', 'NECESSARY']
    }

    @Unroll
    def "Should not create an unnamed new full project"() {
        given:
        def project = new NewProject(name: name, features: [])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_PROJECT_NAME'

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not create a new full project with unnamed feature"() {
        given:
        def feature = new NewFeature(name: name, requirement: 'NECESSARY')
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_FEATURE_NAME'

        where:
        name << [null, '', '  ']
    }

    def "Should not create a new full project with feature without requirement"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: null)
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_FEATURE_REQUIREMENT'
    }

    def "Should not create a new full project with feature with invalid requirement"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: 'Not a requirement')
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'INVALID_FEATURE_REQUIREMENT'
    }
    //-----
    @Unroll
    def "Should update a project setting a #requirement feature with #featureStatus status and browse it"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: requirement)
        def project = new NewProject(name: 'Project 1', features: [feature])
        post('/projects', project)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def projectFeature = new ProjectFeature(name: 'Feature 2', status: featureStatus, requirement: requirement)
        def updatedProject = new UpdatedProject(name: 'Project 2', team: 'Team 2', features: [projectFeature])

        when:
        def response = put("/projects/$projectIdentifier", updatedProject)

        then:
        response.statusCode == NO_CONTENT

        when:
        response = get("/projects/$projectIdentifier", ExistingProject)

        then:
        response.statusCode == OK
        response.body != null
        with(response.body) {
            identifier == projectIdentifier
            name == 'Project 2'
            status == 'TO_DO'
            features != null
            features.size() == 1
            features[0].name == 'Feature 2'
            features[0].status == featureStatus
            features[0].requirement == requirement
        }

        where:
        featureStatus | requirement
        'TO_DO'       | 'OPTIONAL'
        'IN_PROGRESS' | 'RECOMMENDED'
        'DONE'        | 'NECESSARY'
    }

    @Unroll
    def "Should not create an unnamed new full project"() {
        given:
        def project = new NewProject(name: name, features: [])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_PROJECT_NAME'

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not create a new full project with unnamed feature"() {
        given:
        def feature = new NewFeature(name: name, requirement: 'NECESSARY')
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_FEATURE_NAME'

        where:
        name << [null, '', '  ']
    }

    def "Should not create a new full project with feature without requirement"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: null)
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_FEATURE_REQUIREMENT'
    }

    def "Should not create a new full project with feature with invalid requirement"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: 'Not a requirement')
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'INVALID_FEATURE_REQUIREMENT'
    }
}

package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpecification
import com.github.mkopylec.projectmanager.application.dto.ExistingProject
import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft
import com.github.mkopylec.projectmanager.application.dto.ExistingTeam
import com.github.mkopylec.projectmanager.application.dto.NewFeature
import com.github.mkopylec.projectmanager.application.dto.NewProject
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft
import com.github.mkopylec.projectmanager.application.dto.NewTeam
import com.github.mkopylec.projectmanager.application.dto.ProjectEndingCondition
import com.github.mkopylec.projectmanager.application.dto.ProjectFeature
import com.github.mkopylec.projectmanager.application.dto.UpdatedProject
import org.springframework.core.ParameterizedTypeReference
import spock.lang.Unroll

import static com.github.mkopylec.projectmanager.domain.values.Requirement.NECESSARY
import static com.github.mkopylec.projectmanager.domain.values.Requirement.OPTIONAL
import static com.github.mkopylec.projectmanager.domain.values.Status.DONE
import static com.github.mkopylec.projectmanager.domain.values.Status.IN_PROGRESS
import static com.github.mkopylec.projectmanager.domain.values.Status.TO_DO
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
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
            team == null
            features == []
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
            team == null
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

    @Unroll
    def "Should not create a new full project with feature with #requirement requirement"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: requirement)
        def project = new NewProject(name: 'Project 1', features: [feature])

        when:
        def response = post('/projects', project)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == errorCode

        where:
        requirement           | errorCode
        null                  | 'EMPTY_FEATURE_REQUIREMENT'
        ''                    | 'EMPTY_FEATURE_REQUIREMENT'
        '  '                  | 'EMPTY_FEATURE_REQUIREMENT'
        'INVALID_REQUIREMENT' | 'INVALID_FEATURE_REQUIREMENT'
    }

    @Unroll
    def "Should update a project setting a #requirement feature with #featureStatus status and browse it"() {
        given:
        def feature = new NewFeature(name: 'Feature 1', requirement: requirement)
        def project = new NewProject(name: 'Project 1', features: [feature])
        post('/projects', project)
        def newTeam = new NewTeam(name: 'Team 2')
        post('/teams', newTeam)
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
            team == 'Team 2'
            features != null
            features.size() == 1
            features[0].name == 'Feature 2'
            features[0].status == featureStatus
            features[0].requirement == requirement
        }

        when:
        response = get('/teams', new ParameterizedTypeReference<List<ExistingTeam>>() {})

        then:
        response.statusCode == OK
        response.body != null
        response.body.size() == 1
        with(response.body[0]) {
            name == 'Team 2'
            currentlyImplementedProjects == 1
            !busy
            members == []
        }

        where:
        featureStatus | requirement
        'TO_DO'       | 'OPTIONAL'
        'IN_PROGRESS' | 'RECOMMENDED'
        'DONE'        | 'NECESSARY'
    }

    @Unroll
    def "Should not update a project with an empty name"() {
        given:
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def updatedProject = new UpdatedProject(name: name, features: [])

        when:
        def response = put("/projects/$projectIdentifier", updatedProject)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_PROJECT_NAME'

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not update a project with unnamed feature"() {
        given:
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def projectFeature = new ProjectFeature(name: name, status: 'IN_PROGRESS', requirement: 'OPTIONAL')
        def updatedProject = new UpdatedProject(name: 'Project 1', features: [projectFeature])

        when:
        def response = put("/projects/$projectIdentifier", updatedProject)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_FEATURE_NAME'

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not update a project with feature with #status status or #requirement requirement"() {
        given:
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def projectFeature = new ProjectFeature(name: 'Feature 1', status: status, requirement: requirement)
        def updatedProject = new UpdatedProject(name: 'Project 1', features: [projectFeature])

        when:
        def response = put("/projects/$projectIdentifier", updatedProject)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == errorCode

        where:
        status           | requirement           || errorCode
        null             | 'OPTIONAL'            || 'EMPTY_FEATURE_STATUS'
        ''               | 'OPTIONAL'            || 'EMPTY_FEATURE_STATUS'
        '  '             | 'OPTIONAL'            || 'EMPTY_FEATURE_STATUS'
        'INVALID_STATUS' | 'OPTIONAL'            || 'INVALID_FEATURE_STATUS'
        'DONE'           | null                  || 'EMPTY_FEATURE_REQUIREMENT'
        'DONE'           | ''                    || 'EMPTY_FEATURE_REQUIREMENT'
        'DONE'           | '  '                  || 'EMPTY_FEATURE_REQUIREMENT'
        'DONE'           | 'INVALID_REQUIREMENT' || 'INVALID_FEATURE_REQUIREMENT'
    }

    def "Should browse projects if none exists"() {
        when:
        def response = get('/projects', List)

        then:
        response.statusCode == OK
        response.body == []
    }

    def "Should not browse project if it does not exist"() {
        when:
        def response = get('/projects/abc', Map)

        then:
        response.statusCode == NOT_FOUND
        response.body.code == 'NONEXISTENT_PROJECT'
    }

    def "Should start a project"() {
        given:
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def updatedProject = new UpdatedProject(name: 'Project 1', team: 'Team 1', features: [])
        put("/projects/$projectIdentifier", updatedProject)

        when:
        def response = patch("/projects/$projectIdentifier/started")

        then:
        response.statusCode == NO_CONTENT
        with(get("/projects/$projectIdentifier", ExistingProject).body) {
            status == 'IN_PROGRESS'
        }
    }

    def "Should not start an already started project"() {
        given:
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def updatedProject = new UpdatedProject(name: 'Project 1', team: 'Team 1', features: [])
        put("/projects/$projectIdentifier", updatedProject)
        patch("/projects/$projectIdentifier/started")

        when:
        def response = patch("/projects/$projectIdentifier/started")

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'PROJECT_ALREADY_STARTED'
    }

    def "Should not start a project when no team is assigned to it"() {
        given:
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier

        when:
        def response = patch("/projects/$projectIdentifier/started")

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'UNASSIGNED_TEAM'
    }

    def "Should not start a nonexistent project"() {
        when:
        def response = patch('/projects/nonexistent project/started')

        then:
        response.statusCode == NOT_FOUND
        response.body.code == 'NONEXISTENT_PROJECT'
    }

    @Unroll
    def "Should end a project when ending condition is fulfilled"() {
        given:
        stubReportingService()
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def updatedProject = new UpdatedProject(name: 'Project 1', team: 'Team 1', features: features)
        put("/projects/$projectIdentifier", updatedProject)
        patch("/projects/$projectIdentifier/started")
        def endingCondition = new ProjectEndingCondition(onlyNecessaryFeatureDone: onlyNecessaryFeatureDone)

        when:
        def response = patch("/projects/$projectIdentifier/ended", endingCondition)

        then:
        response.statusCode == NO_CONTENT
        with(get("/projects/$projectIdentifier", ExistingProject).body) {
            status.toString() == 'DONE'
        }
        verifyReportWasSent(projectIdentifier)

        where:
        features                                                                            | onlyNecessaryFeatureDone
        []                                                                                  | true
        []                                                                                  | false
        [new ProjectFeature(name: 'Feature 1', status: DONE, requirement: NECESSARY)]       | true
        [new ProjectFeature(name: 'Feature 1', status: IN_PROGRESS, requirement: OPTIONAL)] | true
        [new ProjectFeature(name: 'Feature 1', status: DONE, requirement: NECESSARY)]       | false
    }

    @Unroll
    def "Should not end a project when ending condition is not fulfilled"() {
        given:
        stubReportingService()
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def updatedProject = new UpdatedProject(name: 'Project 1', team: 'Team 1', features: features)
        put("/projects/$projectIdentifier", updatedProject)
        patch("/projects/$projectIdentifier/started")
        def endingCondition = new ProjectEndingCondition(onlyNecessaryFeatureDone: onlyNecessaryFeatureDone)

        when:
        def response = patch("/projects/$projectIdentifier/ended", endingCondition)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'PROJECT_ENDING_CONDITION_NOT_FULFILLED'
        verifyReportWasNotSent(projectIdentifier)

        where:
        features                                                                            | onlyNecessaryFeatureDone
        [new ProjectFeature(name: 'Feature 1', status: TO_DO, requirement: NECESSARY)]      | true
        [new ProjectFeature(name: 'Feature 1', status: IN_PROGRESS, requirement: OPTIONAL)] | false
    }

    def "Should not end an unstarted project"() {
        given:
        stubReportingService()
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def endingCondition = new ProjectEndingCondition(onlyNecessaryFeatureDone: false)

        when:
        def response = patch("/projects/$projectIdentifier/ended", endingCondition)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'UNSTARTED_PROJECT'
        verifyReportWasNotSent(projectIdentifier)
    }

    def "Should not end an already ended project"() {
        given:
        stubReportingService()
        def project = new NewProject(name: 'Project 1', features: [])
        post('/projects', project)
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def projectIdentifier = get('/projects', new ParameterizedTypeReference<List<ExistingProjectDraft>>() {}).body[0].identifier
        def updatedProject = new UpdatedProject(name: 'Project 1', team: 'Team 1', features: [])
        put("/projects/$projectIdentifier", updatedProject)
        patch("/projects/$projectIdentifier/started")
        def endingCondition = new ProjectEndingCondition(onlyNecessaryFeatureDone: false)
        patch("/projects/$projectIdentifier/ended", endingCondition)
        verifyReportWasSent(projectIdentifier)

        when:
        def response = patch("/projects/$projectIdentifier/ended", endingCondition)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'PROJECT_ALREADY_ENDED'
        verifyReportWasSent(projectIdentifier)
    }

    def "Should not end a nonexistent project"() {
        given:
        stubReportingService()
        def endingCondition = new ProjectEndingCondition(onlyNecessaryFeatureDone: false)

        when:
        def response = patch('/projects/nonexistent project/ended', endingCondition)

        then:
        response.statusCode == NOT_FOUND
        response.body.code == 'NONEXISTENT_PROJECT'
        verifyReportWasNotSent('nonexistent project')
    }
}

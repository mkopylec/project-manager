package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.core.project.dto.NewFeature
import com.github.mkopylec.projectmanager.core.project.dto.NewProject
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft
import com.github.mkopylec.projectmanager.core.project.dto.ProjectEndingCondition
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProject
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProjectFeature
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam
import spock.lang.Unroll

import static com.github.mkopylec.projectmanager.core.project.dto.Requirement.NECESSARY
import static com.github.mkopylec.projectmanager.core.project.dto.Requirement.OPTIONAL
import static com.github.mkopylec.projectmanager.core.project.dto.Requirement.RECOMMENDED
import static com.github.mkopylec.projectmanager.core.project.dto.Status.DONE
import static com.github.mkopylec.projectmanager.core.project.dto.Status.IN_PROGRESS
import static com.github.mkopylec.projectmanager.core.project.dto.Status.TO_DO
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class ProjectSpecification extends BasicSpecification {

    def "Should create new project draft and browse it"() {
        given:
        def projectDraft = new NewProjectDraft('Project 1')

        when:
        def response = httpClient.createProject(projectDraft)

        then:
        with(response) {
            status == CREATED
        }

        when:
        response = httpClient.getProjects()

        then:
        with(response) {
            status == OK
            body.size() == 1
            with(body[0]) {
                identifier
                name == 'Project 1'
            }
        }

        and:
        def projectIdentifier = response.body[0].identifier

        when:
        response = httpClient.getProject(projectIdentifier)

        then:
        with(response) {
            status == OK
            with(body) {
                identifier == projectIdentifier
                name == 'Project 1'
                status == TO_DO
                !team
                features == []
            }
        }
    }

    @Unroll
    def "Should not create an unnamed new project draft"() {
        given:
        def projectDraft = new NewProjectDraft(name)

        when:
        def response = httpClient.createProject(projectDraft)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'create project draft' has failed"
                codes == ['EMPTY_PROJECT_NAME']
            }
        }

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should create new full project with a #requirement feature and browse it"() {
        given:
        def feature = new NewFeature('Feature 1', requirement)
        def project = new NewProject('Project 1', [feature])

        when:
        def response = httpClient.createProject(project)

        then:
        with(response) {
            status == CREATED
        }

        when:
        response = httpClient.getProjects()

        then:
        with(response) {
            status == OK
            body.size() == 1
            with(body[0]) {
                identifier
                name == 'Project 1'
            }
        }

        and:
        def projectIdentifier = response.body[0].identifier

        when:
        response = httpClient.getProject(projectIdentifier)

        then:
        with(response) {
            status == OK
            with(body) {
                identifier == projectIdentifier
                name == 'Project 1'
                status == TO_DO
                !team
                features.size() == 1
                with(features[0]) {
                    name == 'Feature 1'
                    status == TO_DO
                    requirement == requirement
                }
            }
        }

        where:
        requirement << [OPTIONAL, RECOMMENDED, NECESSARY]
    }

    @Unroll
    def "Should not create an unnamed new full project"() {
        given:
        def project = new NewProject(name, [])

        when:
        def response = httpClient.createProject(project)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'create project' has failed"
                codes == ['EMPTY_PROJECT_NAME']
            }
        }

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not create a new full project with unnamed feature"() {
        given:
        def feature = new NewFeature(name, NECESSARY)
        def project = new NewProject('Project 1', [feature])

        when:
        def response = httpClient.createProject(project)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'create project' has failed"
                codes == ['EMPTY_PROJECT_FEATURE_NAME']
            }
        }

        where:
        name << [null, '', '  ']
    }

    def "Should not create a new full project with feature with no requirement"() {
        given:
        def feature = new NewFeature('Feature 1', null)
        def project = new NewProject('Project 1', [feature])

        when:
        def response = httpClient.createProject(project)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'create project' has failed"
                codes == ['EMPTY_PROJECT_FEATURE_REQUIREMENT']
            }
        }
    }

    @Unroll
    def "Should update a project setting a #requirement feature with #featureStatus status and browse it"() {
        given:
        def feature = new NewFeature('Feature 1', requirement)
        def project = new NewProject('Project 1', [feature])
        httpClient.createProject(project)
        def newTeam = new NewTeam('Team 2')
        httpClient.createTeam(newTeam)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def projectFeature = new UpdatedProjectFeature('Feature 2', featureStatus, requirement)
        def updatedProject = new UpdatedProject(null, 'Project 2', 'Team 2', [projectFeature])

        when:
        def response = httpClient.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == NO_CONTENT
        }

        when:
        response = httpClient.getProject(projectIdentifier)

        then:
        with(response) {
            status == OK
            with(body) {
                identifier == projectIdentifier
                name == 'Project 2'
                status == TO_DO
                team == 'Team 2'
                features.size() == 1
                with(features[0]) {
                    name == 'Feature 2'
                    status == featureStatus
                    requirement == requirement
                }
            }
        }

        when:
        response = httpClient.getTeams()

        then:
        with(response) {
            status == OK
            body.size() == 1
            with(body[0]) {
                name == 'Team 2'
                currentlyImplementedProjects == 1
            }
        }

        where:
        featureStatus | requirement
        TO_DO         | OPTIONAL
        IN_PROGRESS   | RECOMMENDED
        DONE          | NECESSARY
    }

    @Unroll
    def "Should not update a project with an empty name"() {
        given:
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def updatedProject = new UpdatedProject(null, name, null, [])

        when:
        def response = httpClient.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'update project' has failed"
                codes == ['EMPTY_PROJECT_NAME']
            }
        }

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not update a project with unnamed feature"() {
        given:
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def projectFeature = new UpdatedProjectFeature(name, IN_PROGRESS, OPTIONAL)
        def updatedProject = new UpdatedProject(null, 'Project 1', null, [projectFeature])

        when:
        def response = httpClient.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'update project' has failed"
                codes == ['EMPTY_PROJECT_FEATURE_NAME']
            }
        }

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not update a project with feature with #featureStatus status or #requirement requirement"() {
        given:
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def projectFeature = new UpdatedProjectFeature('Feature 1', featureStatus, requirement)
        def updatedProject = new UpdatedProject(null, 'Project 1', null, [projectFeature])

        when:
        def response = httpClient.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'update project' has failed"
                codes == [errorCode]
            }
        }

        where:
        featureStatus | requirement || errorCode
        null          | OPTIONAL    || 'EMPTY_PROJECT_FEATURE_STATUS'
        DONE          | null        || 'EMPTY_PROJECT_FEATURE_REQUIREMENT'
    }

    def "Should not update a project when team does not exist"() {
        given:
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def updatedProject = new UpdatedProject(null, 'Project 1', 'Nonexistent team', [])

        when:
        def response = httpClient.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'update project' has failed"
                codes == ['MISSING_ASSIGNED_TEAM']
            }
        }
    }

    def "Should browse projects if none exists"() {
        when:
        def response = httpClient.getProjects()

        then:
        with(response) {
            status == OK
            body.isEmpty()
        }
    }

    def "Should browse project if it does not exist"() {
        when:
        def response = httpClient.getProject('abc')

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                message == "Use case 'get project' has failed"
                codes == ['MISSING_PROJECT']
            }
        }
    }

    def "Should start a project"() {
        given:
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def updatedProject = new UpdatedProject(null, 'Project 1', 'Team 1', [])
        httpClient.updateProject(projectIdentifier, updatedProject)

        when:
        def response = httpClient.startProject(projectIdentifier)

        then:
        with(response) {
            status == NO_CONTENT
        }

        when:
        response = httpClient.getProject(projectIdentifier)

        then:
        with(response.body) {
            status == IN_PROGRESS
        }
    }

    def "Should not start an already started project"() {
        given:
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def updatedProject = new UpdatedProject(null, 'Project 1', 'Team 1', [])
        httpClient.updateProject(projectIdentifier, updatedProject)
        httpClient.startProject(projectIdentifier)

        when:
        def response = httpClient.startProject(projectIdentifier)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'start project' has failed"
                codes == ['PROJECT_STATUS_DIFFERENT_THAN_TO_DO']
            }
        }
    }

    def "Should not start a project when no team is assigned to it"() {
        given:
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def projectIdentifier = httpClient.getProjects().body[0].identifier

        when:
        def response = httpClient.startProject(projectIdentifier)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'start project' has failed"
                codes == ['EMPTY_PROJECT_ASSIGNED_TEAM']
            }
        }
    }

    def "Should not start a nonexistent project"() {
        when:
        def response = httpClient.startProject('nonexistent project')

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                message == "Use case 'start project' has failed"
                codes == ['MISSING_PROJECT']
            }
        }
    }

    @Unroll
    def "Should end a project when ending condition is fulfilled"() {
        given:
        reportingService.stubReportReceiving()
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def updatedProject = new UpdatedProject(null, 'Project 1', 'Team 1', features)
        httpClient.updateProject(projectIdentifier, updatedProject)
        httpClient.startProject(projectIdentifier)
        def endingCondition = new ProjectEndingCondition(null, onlyNecessaryFeatureDone)

        when:
        def response = httpClient.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == NO_CONTENT
        }

        when:
        response = httpClient.getProject(projectIdentifier)

        then:
        with(response.body) {
            status == DONE
        }

        when:
        response = httpClient.getTeams()

        then:
        with(response.body[0]) {
            name == 'Team 1'
            currentlyImplementedProjects == 0
        }
        reportingService.verifyReportWasSent(projectIdentifier)

        where:
        features | onlyNecessaryFeatureDone
        []       | true
//        []                                                              | false
//        [new UpdatedProjectFeature('Feature 1', DONE, NECESSARY)]       | true
//        [new UpdatedProjectFeature('Feature 1', IN_PROGRESS, OPTIONAL)] | true
//        [new UpdatedProjectFeature('Feature 1', DONE, NECESSARY)]       | false
    }

    @Unroll
    def "Should not end a project when ending condition is not fulfilled"() {
        given:
        reportingService.stubReportReceiving()
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def updatedProject = new UpdatedProject(null, 'Project 1', 'Team 1', features)
        httpClient.updateProject(projectIdentifier, updatedProject)
        httpClient.startProject(projectIdentifier)
        def endingCondition = new ProjectEndingCondition(null, onlyNecessaryFeatureDone)

        when:
        def response = httpClient.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'end project' has failed"
                codes == [errorCode]
            }
        }
        reportingService.verifyReportWasNotSent(projectIdentifier)

        where:
        features                                                        | onlyNecessaryFeatureDone || errorCode
        [new UpdatedProjectFeature('Feature 1', TO_DO, NECESSARY)]      | true                     || 'UNDONE_PROJECT_NECESSARY_FEATURE'
        [new UpdatedProjectFeature('Feature 1', IN_PROGRESS, OPTIONAL)] | false                    || 'UNDONE_PROJECT_FEATURE'
    }

    def "Should not end an unstarted project"() {
        given:
        reportingService.stubReportReceiving()
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def endingCondition = new ProjectEndingCondition(null, false)

        when:
        def response = httpClient.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'end project' has failed"
                codes == ['PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS']
            }
        }
        reportingService.verifyReportWasNotSent(projectIdentifier)
    }

    def "Should not end an already ended project"() {
        given:
        reportingService.stubReportReceiving()
        def project = new NewProject('Project 1', [])
        httpClient.createProject(project)
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def projectIdentifier = httpClient.getProjects().body[0].identifier
        def updatedProject = new UpdatedProject(null, 'Project 1', 'Team 1', [])
        httpClient.updateProject(projectIdentifier, updatedProject)
        httpClient.startProject(projectIdentifier)
        def endingCondition = new ProjectEndingCondition(null, false)
        httpClient.endProject(projectIdentifier, endingCondition)
        reportingService.verifyReportWasSent(projectIdentifier)
        reportingService.clear()

        when:
        def response = httpClient.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                message == "Use case 'end project' has failed"
                codes == ['PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS']
            }
        }
        reportingService.verifyReportWasNotSent(projectIdentifier)
    }

    def "Should not end a nonexistent project"() {
        given:
        reportingService.stubReportReceiving()
        def endingCondition = new ProjectEndingCondition(null, false)

        when:
        def response = httpClient.endProject('nonexistent project', endingCondition)

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                message == "Use case 'end project' has failed"
                codes == ['MISSING_PROJECT']
            }
        }
        reportingService.verifyReportWasNotSent('nonexistent project')
    }
}

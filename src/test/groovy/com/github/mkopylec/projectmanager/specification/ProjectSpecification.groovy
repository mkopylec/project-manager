package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.api.dto.NewFeature
import com.github.mkopylec.projectmanager.api.dto.NewProject
import com.github.mkopylec.projectmanager.api.dto.NewProjectDraft
import com.github.mkopylec.projectmanager.api.dto.ProjectEndingCondition
import com.github.mkopylec.projectmanager.api.dto.UpdatedProject
import com.github.mkopylec.projectmanager.api.dto.UpdatedProjectFeature
import com.github.mkopylec.projectmanager.api.exception.InvalidEntityException
import com.github.mkopylec.projectmanager.core.project.Project
import com.github.mkopylec.projectmanager.core.project.Requirement
import com.github.mkopylec.projectmanager.core.project.Status
import com.github.mkopylec.projectmanager.core.team.Team
import com.github.mkopylec.projectmanager.utils.EndedProjectBuilder
import com.github.mkopylec.projectmanager.utils.FeatureBuilder
import com.github.mkopylec.projectmanager.utils.ProjectBuilder
import com.github.mkopylec.projectmanager.utils.TeamBuilder
import spock.lang.Unroll

import static com.github.mkopylec.projectmanager.core.project.Requirement.OPTIONAL
import static com.github.mkopylec.projectmanager.core.project.Status.DONE
import static com.github.mkopylec.projectmanager.core.project.Status.IN_PROGRESS
import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO
import static java.util.UUID.randomUUID
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class ProjectSpecification extends BasicSpecification {

    def "Should create new project draft"() {
        given:
        def projectDraft = new NewProjectDraft('Project 1')

        when:
        def response = http.createProject(projectDraft)

        then:
        with(response) {
            status == CREATED
        }
        mongoDb.expectInserted(new ProjectBuilder(
                name: projectDraft.name,
                status: TO_DO,
                assignedTeam: null,
                features: null))
    }

    @Unroll
    def "Should not create an unnamed new project draft"() {
        given:
        def projectDraft = new NewProjectDraft(name)

        when:
        def response = http.createProject(projectDraft)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_PROJECT_DRAFT_NAME']
            }
        }
        mongoDb.expectNotSaved(Project)

        when:
        projectManager.createProject(projectDraft)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_PROJECT_NAME'
        mongoDb.expectNotSaved(Project)

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should create new full project with a #requirement feature"() {
        given:
        def feature = new NewFeature('Feature 1', requirement)
        def project = new NewProject('Project 1', [feature])

        when:
        def response = http.createProject(project)

        then:
        with(response) {
            status == CREATED
        }
        mongoDb.expectInserted(new ProjectBuilder(
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: [new FeatureBuilder(
                        name: 'Feature 1',
                        status: TO_DO,
                        requirement: Requirement.valueOf(requirement))]))

        where:
        requirement << ['OPTIONAL', 'RECOMMENDED', 'NECESSARY']
    }

    @Unroll
    def "Should not create an unnamed new full project"() {
        given:
        def project = new NewProject(name, [])

        when:
        def response = http.createProject(project)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_PROJECT_NAME']
            }
        }
        mongoDb.expectNotSaved(Project)

        when:
        projectManager.createProject(project)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_PROJECT_NAME'
        mongoDb.expectNotSaved(Project)

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not create a new full project with unnamed feature"() {
        given:
        def feature = new NewFeature(name, 'NECESSARY')
        def project = new NewProject('Project 1', [feature])

        when:
        def response = http.createProject(project)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_FEATURE_NAME']
            }
        }
        mongoDb.expectNotSaved(Project)

        when:
        projectManager.createProject(project)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_FEATURE_NAME'
        mongoDb.expectNotSaved(Project)

        where:
        name << [null, '', '  ']
    }

    def "Should not create a new full project with feature with no requirement"() {
        given:
        def feature = new NewFeature('Feature 1', null)
        def project = new NewProject('Project 1', [feature])

        when:
        def response = http.createProject(project)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_FEATURE_REQUIREMENT']
            }
        }
        mongoDb.expectNotSaved(Project)

        when:
        projectManager.createProject(project)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_FEATURE_REQUIREMENT'
        mongoDb.expectNotSaved(Project)
    }

    def "Should browse projects if any exists"() {
        given:
        def projectIdentifier1 = randomUUID()
        def projectIdentifier2 = randomUUID()
        mongoDb.stubFindingAllProjects([
                new ProjectBuilder(
                        identifier: projectIdentifier1,
                        name: 'Project 1',
                        status: TO_DO,
                        assignedTeam: null,
                        features: [new FeatureBuilder(
                                name: 'Feature 1',
                                status: TO_DO,
                                requirement: OPTIONAL)]),
                new ProjectBuilder(
                        identifier: projectIdentifier2,
                        name: 'Project 2',
                        status: IN_PROGRESS,
                        assignedTeam: 'Team 1',
                        features: [])])

        when:
        def response = http.getProjects()

        then:
        with(response) {
            status == OK
            body.size() == 2
            with(body[0]) {
                identifier == projectIdentifier1
                name == 'Project 1'
            }
            with(body[1]) {
                identifier == projectIdentifier2
                name == 'Project 2'
            }
        }
    }

    def "Should not browse projects if none exists"() {
        given:
        mongoDb.stubFindingAllProjects([])

        when:
        def response = http.getProjects()

        then:
        with(response) {
            status == OK
            body.isEmpty()
        }
    }

    def "Should browse project if it exists"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: [new FeatureBuilder(
                        name: 'Feature 1',
                        status: TO_DO,
                        requirement: OPTIONAL)]))

        when:
        def response = http.getProject(projectIdentifier)

        then:
        with(response) {
            status == OK
            with(body) {
                identifier == projectIdentifier
                name == 'Project 1'
                status == 'TO_DO'
                !team
                features.size() == 1
                with(features[0]) {
                    name == 'Feature 1'
                    status == 'TO_DO'
                    requirement == 'OPTIONAL'
                }
            }
        }
    }

    def "Should not browse project if it does not exist"() {
        when:
        def response = http.getProject(randomUUID())

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                violations == ['MISSING_PROJECT']
            }
        }
    }

    @Unroll
    def "Should update a project setting a #requirement feature with #featureStatus status"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: [new FeatureBuilder(
                        name: 'Feature 1',
                        status: TO_DO,
                        requirement: Requirement.valueOf(requirement))]))
        mongoDb.stubFindingById(new TeamBuilder(
                name: 'Team 2',
                currentlyImplementedProjects: 0,
                members: []))
        def projectFeature = new UpdatedProjectFeature('Feature 2', featureStatus, requirement)
        def updatedProject = new UpdatedProject('Project 2', 'Team 2', [projectFeature])

        when:
        def response = http.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == NO_CONTENT
        }
        mongoDb.expectUpdated(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 2',
                status: TO_DO,
                assignedTeam: 'Team 2',
                features: [new FeatureBuilder(
                        name: 'Feature 2',
                        status: Status.valueOf(featureStatus),
                        requirement: Requirement.valueOf(requirement))]))
        mongoDb.expectSaved(new TeamBuilder(
                name: 'Team 2',
                currentlyImplementedProjects: 1,
                members: []))

        where:
        featureStatus | requirement
        'TO_DO'       | 'OPTIONAL'
        'IN_PROGRESS' | 'RECOMMENDED'
        'DONE'        | 'NECESSARY'
    }

    @Unroll
    def "Should not update a project with an empty name"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: []))
        def updatedProject = new UpdatedProject(name, null, [])

        when:
        def response = http.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_UPDATED_PROJECT_NAME']
            }
        }
        mongoDb.expectNotSaved(Project)

        when:
        projectManager.updateProject(projectIdentifier, updatedProject)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_PROJECT_NAME'
        mongoDb.expectNotSaved(Project)

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not update a project with unnamed feature"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: []))
        def projectFeature = new UpdatedProjectFeature(name, 'IN_PROGRESS', 'OPTIONAL')
        def updatedProject = new UpdatedProject('Project 1', null, [projectFeature])

        when:
        def response = http.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_UPDATED_PROJECT_FEATURE_NAME']
            }
        }
        mongoDb.expectNotSaved(Project)

        when:
        projectManager.updateProject(projectIdentifier, updatedProject)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_FEATURE_NAME'
        mongoDb.expectNotSaved(Project)

        where:
        name << [null, '', '  ']
    }

    @Unroll
    def "Should not update a project with feature with #status status or #requirement requirement"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: []))
        def projectFeature = new UpdatedProjectFeature('Feature 1', featureStatus, requirement)
        def updatedProject = new UpdatedProject('Project 1', null, [projectFeature])

        when:
        def response = http.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == [apiErrorCode]
            }
        }
        mongoDb.expectNotSaved(Project)

        when:
        projectManager.updateProject(projectIdentifier, updatedProject)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == applicationErrorCode
        mongoDb.expectNotSaved(Project)

        where:
        featureStatus | requirement || apiErrorCode                                | applicationErrorCode
        null          | 'OPTIONAL'  || 'EMPTY_UPDATED_PROJECT_FEATURE_STATUS'      | 'EMPTY_FEATURE_STATUS'
        'DONE'        | null        || 'EMPTY_UPDATED_PROJECT_FEATURE_REQUIREMENT' | 'EMPTY_FEATURE_REQUIREMENT'
    }

    def "Should not update a project when team does not exist"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: []))
        def updatedProject = new UpdatedProject('Project 1', 'Nonexistent team', [])

        when:
        def response = http.updateProject(projectIdentifier, updatedProject)

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                violations == ['MISSING_TEAM_ASSIGNED_TO_PROJECT']
            }
        }
    }

    def "Should start a project"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: 'Team 1',
                features: []))

        when:
        def response = http.startProject(projectIdentifier)

        then:
        with(response) {
            status == NO_CONTENT
        }
        mongoDb.expectUpdated(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: IN_PROGRESS,
                assignedTeam: 'Team 1',
                features: []))
    }

    def "Should not start an already started project"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: IN_PROGRESS,
                assignedTeam: 'Team 1',
                features: []))

        when:
        def response = http.startProject(projectIdentifier)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['PROJECT_STATUS_DIFFERENT_THAN_TO_DO']
            }
        }
        mongoDb.expectNotSaved(Project)
    }

    def "Should not start a project when no team is assigned to it"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: []))

        when:
        def response = http.startProject(projectIdentifier)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_TEAM_ASSIGNED_TO_PROJECT']
            }
        }
        mongoDb.expectNotSaved(Project)
    }

    def "Should not start a nonexistent project"() {
        when:
        def response = http.startProject(randomUUID())

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                violations == ['MISSING_PROJECT']
            }
        }
        mongoDb.expectNotSaved(Project)
    }

    @Unroll
    def "Should end a project when ending condition is fulfilled"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: IN_PROGRESS,
                assignedTeam: 'Team 1',
                features: features))
        mongoDb.stubFindingById(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 1,
                members: []))
        def endingCondition = new ProjectEndingCondition(onlyNecessaryFeatureDone)

        when:
        def response = http.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == NO_CONTENT
        }
        mongoDb.expectUpdated(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: DONE,
                assignedTeam: 'Team 1',
                features: features))
        mongoDb.expectSaved(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: []))
        reportingService.verifyReportWasSent(new EndedProjectBuilder(
                projectIdentifier: projectIdentifier))

        where:
        features                                                                                | onlyNecessaryFeatureDone
        []                                                                                      | true
        []                                                                                      | false
        [new FeatureBuilder(name: 'Feature 1', status: 'DONE', requirement: 'NECESSARY')]       | true
        [new FeatureBuilder(name: 'Feature 1', status: 'IN_PROGRESS', requirement: 'OPTIONAL')] | true
        [new FeatureBuilder(name: 'Feature 1', status: 'DONE', requirement: 'NECESSARY')]       | false
    }

    @Unroll
    def "Should not end a project when ending condition is not fulfilled"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: IN_PROGRESS,
                assignedTeam: 'Team 1',
                features: features))
        def endingCondition = new ProjectEndingCondition(onlyNecessaryFeatureDone)

        when:
        def response = http.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == [errorCode]
            }
        }
        mongoDb.expectNotSaved(Project)
        mongoDb.expectNotSaved(Team)
        reportingService.verifyReportWasNotSent()

        where:
        features                                                                                | onlyNecessaryFeatureDone || errorCode
        [new FeatureBuilder(name: 'Feature 1', status: 'TO_DO', requirement: 'NECESSARY')]      | true                     || 'UNDONE_PROJECT_NECESSARY_FEATURE'
        [new FeatureBuilder(name: 'Feature 1', status: 'IN_PROGRESS', requirement: 'OPTIONAL')] | false                    || 'UNDONE_PROJECT_FEATURE'
    }

    def "Should not end an unstarted project"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: TO_DO,
                assignedTeam: null,
                features: []))
        def endingCondition = new ProjectEndingCondition(false)

        when:
        def response = http.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS']
            }
        }
        mongoDb.expectNotSaved(Project)
        mongoDb.expectNotSaved(Team)
        reportingService.verifyReportWasNotSent()
    }

    def "Should not end an already ended project"() {
        given:
        def projectIdentifier = randomUUID()
        mongoDb.stubFindingById(new ProjectBuilder(
                identifier: projectIdentifier,
                name: 'Project 1',
                status: DONE,
                assignedTeam: 'Team 1',
                features: []))
        def endingCondition = new ProjectEndingCondition(false)

        when:
        def response = http.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS']
            }
        }
        mongoDb.expectNotSaved(Project)
        mongoDb.expectNotSaved(Team)
        reportingService.verifyReportWasNotSent()
    }

    def "Should not end a nonexistent project"() {
        given:
        def projectIdentifier = randomUUID()
        def endingCondition = new ProjectEndingCondition(false)

        when:
        def response = http.endProject(projectIdentifier, endingCondition)

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                violations == ['MISSING_PROJECT']
            }
        }
        mongoDb.expectNotSaved(Project)
        mongoDb.expectNotSaved(Team)
        reportingService.verifyReportWasNotSent()
    }
}

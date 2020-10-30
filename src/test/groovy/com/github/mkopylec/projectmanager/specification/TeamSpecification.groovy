package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.api.dto.NewTeam
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember
import com.github.mkopylec.projectmanager.api.exception.InvalidEntityException
import com.github.mkopylec.projectmanager.core.team.JobPosition
import com.github.mkopylec.projectmanager.core.team.Team
import com.github.mkopylec.projectmanager.utils.MemberBuilder
import com.github.mkopylec.projectmanager.utils.TeamBuilder
import spock.lang.Unroll

import static com.github.mkopylec.projectmanager.core.team.JobPosition.DEVELOPER
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class TeamSpecification extends BasicSpecification {

    def "Should create new team"() {
        given:
        def newTeam = new NewTeam('Team 1')

        when:
        def response = http.createTeam(newTeam)

        then:
        with(response) {
            status == CREATED
        }
        mongoDb.expectSaved(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: []))
    }

    @Unroll
    def "Should not create an unnamed new team"() {
        given:
        def newTeam = new NewTeam(name)

        when:
        def response = http.createTeam(newTeam)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_TEAM_NAME']
            }
        }
        mongoDb.expectNotSaved(Team)

        when:
        projectManager.createTeam(newTeam)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_TEAM_NAME'
        mongoDb.expectNotSaved(Team)

        where:
        name << [null, '', '  ']
    }

    def "Should not create a team that already exists"() {
        given:
        mongoDb.stubFindingById(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: []))
        def newTeam = new NewTeam('Team 1')

        when:
        def response = http.createTeam(newTeam)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['TEAM_EXISTS']
            }
        }
        mongoDb.expectNotSaved(Team)
    }

    @Unroll
    def "Should add a new member with #jobPosition job position to a team"() {
        given:
        mongoDb.stubFindingById(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: []))
        def member = new NewTeamMember('Mariusz', 'Kopylec', memberJobPosition)

        when:
        def response = http.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == CREATED
        }
        mongoDb.expectSaved(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: [new MemberBuilder(
                        firstName: 'Mariusz',
                        lastName: 'Kopylec',
                        jobPosition: JobPosition.valueOf(memberJobPosition))]))

        where:
        memberJobPosition << ['DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER']
    }

    @Unroll
    def "Should not add a new member without a first name to a team"() {
        given:
        mongoDb.stubFindingById(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: []))
        def member = new NewTeamMember(firstName, 'Kopylec', 'DEVELOPER')

        when:
        def response = http.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_TEAM_MEMBER_FIRST_NAME']
            }
        }
        mongoDb.expectNotSaved(Team)

        when:
        projectManager.addMemberToTeam('Team 1', member)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_MEMBER_FIRST_NAME'
        mongoDb.expectNotSaved(Team)

        where:
        firstName << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member without a last name to a team"() {
        given:
        mongoDb.stubFindingById(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: []))
        def member = new NewTeamMember('Mariusz', lastName, 'DEVELOPER')

        when:
        def response = http.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_TEAM_MEMBER_LAST_NAME']
            }
        }
        mongoDb.expectNotSaved(Team)

        when:
        projectManager.addMemberToTeam('Team 1', member)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_MEMBER_LAST_NAME'
        mongoDb.expectNotSaved(Team)

        where:
        lastName << [null, '', '  ']
    }

    def "Should not add a new member without job position to a team"() {
        given:
        mongoDb.stubFindingById(new TeamBuilder(
                name: 'Team 1',
                currentlyImplementedProjects: 0,
                members: []))
        def member = new NewTeamMember('Mariusz', 'Kopylec', null)

        when:
        def response = http.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            with(failure) {
                violations == ['EMPTY_NEW_TEAM_MEMBER_JOB_POSITION']
            }
        }
        mongoDb.expectNotSaved(Team)

        when:
        projectManager.addMemberToTeam('Team 1', member)

        then:
        def e = thrown(InvalidEntityException)
        e.violation.get() == 'EMPTY_MEMBER_JOB_POSITION'
        mongoDb.expectNotSaved(Team)
    }

    def "Should not add a new member to a nonexistent team"() {
        given:
        def member = new NewTeamMember('Mariusz', 'Kopylec', 'DEVELOPER')

        when:
        def response = http.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == NOT_FOUND
            with(failure) {
                violations == ['MISSING_TEAM']
            }
        }
        mongoDb.expectNotSaved(Team)
    }

    def "Should not browse teams if any exists"() {
        given:
        mongoDb.stubFindingAllTeams([
                new TeamBuilder(
                        name: 'Team 1',
                        currentlyImplementedProjects: 0,
                        members: [new MemberBuilder(
                                firstName: 'Mariusz',
                                lastName: 'Kopylec',
                                jobPosition: DEVELOPER)]),
                new TeamBuilder(
                        name: 'Team 2',
                        currentlyImplementedProjects: 3,
                        members: [])])

        when:
        def response = http.getTeams()

        then:
        with(response) {
            status == OK
            body.size() == 2
            with(body[0]) {
                name == 'Team 1'
                currentlyImplementedProjects == 0
                members.size() == 1
                with(members[0]) {
                    firstName == 'Mariusz'
                    lastName == 'Kopylec'
                    jobPosition == 'DEVELOPER'
                }
            }
            with(body[1]) {
                name == 'Team 2'
                currentlyImplementedProjects == 3
                members == []
            }
        }
    }

    def "Should not browse teams if none exists"() {
        given:
        mongoDb.stubFindingAllTeams([])

        when:
        def response = http.getTeams()

        then:
        with(response) {
            status == OK
            body.isEmpty()
        }
    }
}

package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.core.NewTeam
import com.github.mkopylec.projectmanager.core.NewTeamMember
import spock.lang.Unroll

import static com.github.mkopylec.projectmanager.core.JobPosition.DEVELOPER
import static com.github.mkopylec.projectmanager.core.JobPosition.PRODUCT_OWNER
import static com.github.mkopylec.projectmanager.core.JobPosition.SCRUM_MASTER
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class TeamSpecification extends BasicSpecification {

    def "Should create new team and browse it"() {
        given:
        def newTeam = new NewTeam('Team 1')

        when:
        def response = httpClient.createTeam(newTeam)

        then:
        with(response) {
            status == CREATED
        }

        when:
        response = httpClient.getTeams()

        then:
        with(response) {
            status == OK
            body.size() == 1
            with(body[0]) {
                name == 'Team 1'
                currentlyImplementedProjects == 0
                !busy
                members == []
            }
        }
    }

    @Unroll
    def "Should not create an unnamed new team"() {
        given:
        def newTeam = new NewTeam(name)

        when:
        def response = httpClient.createTeam(newTeam)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            errors.size() == 1
            with(errors[0]) {
                code == 'EMPTY_TEAM_NAME'
                message == "Error creating '$name' team"
            }
        }

        where:
        name << [null, '', '  ']
    }

    def "Should not create a team that already exists"() {
        given:
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)

        when:
        def response = httpClient.createTeam(newTeam)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            errors.size() == 1
            with(errors[0]) {
                code == 'TEAM_EXISTS'
                message == "Error creating team named 'Team 1'"
            }
        }
    }

    @Unroll
    def "Should add a new member with #jobPosition job position to a team and browse him"() {
        given:
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def member = new NewTeamMember('Mariusz', 'Kopylec', memberJobPosition)

        when:
        def response = httpClient.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == CREATED
        }

        when:
        response = httpClient.getTeams()

        then:
        with(response) {
            status == OK
            body.size() == 1
            with(body[0]) {
                members.size() == 1
                with(members[0]) {
                    firstName == 'Mariusz'
                    lastName == 'Kopylec'
                    jobPosition == memberJobPosition
                }
            }
        }

        where:
        memberJobPosition << [DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER]
    }

    @Unroll
    def "Should not add a new member without a first name to a team"() {
        given:
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def member = new NewTeamMember(firstName, 'Kopylec', DEVELOPER)

        when:
        def response = httpClient.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            errors.size() == 1
            with(errors[0]) {
                code == 'EMPTY_TEAM_MEMBER_FIRST_NAME'
                message == "Error adding member to 'Team 1' team"
            }
        }

        where:
        firstName << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member without a last name to a team"() {
        given:
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def member = new NewTeamMember('Mariusz', lastName, DEVELOPER)

        when:
        def response = httpClient.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            errors.size() == 1
            with(errors[0]) {
                code == 'EMPTY_TEAM_MEMBER_LAST_NAME'
                message == "Error adding member to 'Team 1' team"
            }
        }

        where:
        lastName << [null, '', '  ']
    }

    def "Should not add a new member without job position to a team"() {
        given:
        def newTeam = new NewTeam('Team 1')
        httpClient.createTeam(newTeam)
        def member = new NewTeamMember('Mariusz', 'Kopylec', null)

        when:
        def response = httpClient.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == UNPROCESSABLE_ENTITY
            errors.size() == 1
            with(errors[0]) {
                code == 'EMPTY_TEAM_MEMBER_JOB_POSITION'
                message == "Error adding member to 'Team 1' team"
            }
        }
    }

    def "Should not add a new member to a nonexistent team"() {
        given:
        def member = new NewTeamMember('Mariusz', 'Kopylec', DEVELOPER)

        when:
        def response = httpClient.addMemberToTeam('Team 1', member)

        then:
        with(response) {
            status == NOT_FOUND
            errors.size() == 1
            with(errors[0]) {
                code == 'MISSING_TEAM'
                message == "Error adding member to 'Team 1' team"
            }
        }
    }

    def "Should browse teams if none exists"() {
        when:
        def response = httpClient.getTeams()

        then:
        with(response) {
            status == OK
            body.isEmpty()
        }
    }
}

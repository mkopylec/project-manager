package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpecification
import com.github.mkopylec.projectmanager.application.dto.NewTeam
import com.github.mkopylec.projectmanager.application.dto.TeamMember
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class TeamSpecification extends BasicSpecification {

    def "Should create a new team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')

        when:
        def response = post('/teams', newTeam)

        then:
        response.statusCode == CREATED
    }

    @Unroll
    def "Should not create an unnamed new team"() {
        given:
        def newTeam = new NewTeam(name: name)

        when:
        def response = post('/teams', newTeam)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_TEAM_NAME'

        where:
        name << [null, '', '  ']
    }

    def "Should not create a team that already exists"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)

        when:
        def response = post('/teams', newTeam)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'TEAM_ALREADY_EXISTS'
    }

    @Unroll
    def "Should add a new member with #jobPosition to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: jobPosition)

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == CREATED

        where:
        jobPosition << ['DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER']
    }

    @Unroll
    def "Should not add a new member without a first name to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: firstName, lastName: 'Kopylec', jobPosition: 'DEVELOPER')

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_FIRST_NAME'

        where:
        firstName << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member without a last name to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: 'Mariusz', lastName: lastName, jobPosition: 'DEVELOPER')

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_LAST_NAME'

        where:
        lastName << [null, '', '  ']
    }

    def "Should not add a new member without job position to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec')

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_JOB_POSITION'
    }

    def "Should not add a new member to a nonexistent team"() {
        given:
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: 'DEVELOPER')

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == NOT_FOUND
        response.body.code == 'NONEXISTENT_TEAM'
    }
}

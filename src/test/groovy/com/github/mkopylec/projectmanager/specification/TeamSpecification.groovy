package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpecification
import com.github.mkopylec.projectmanager.application.dto.NewTeam
import com.github.mkopylec.projectmanager.application.dto.NewTeamMember
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.CREATED
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
    def "Should not create a new team with empty name"() {
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

    def "Should not create a new team when a team with the same name already exists"() {
        given:
        def newTeam = new NewTeam(name: 'Team 2')
        post('/teams', newTeam)

        when:
        def response = post('/teams', newTeam)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'TEAM_ALREADY_EXISTS'
    }

    def "Should add a new member to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 3')
        post('/teams', newTeam)
        def member = new NewTeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: 'developer')

        when:
        def response = post('/teams/Team 3/members', member)

        then:
        response.statusCode == CREATED
    }

    @Unroll
    def "Should not a new member to a team with empty first name"() {
        given:
        def newTeam = new NewTeam(name: 'Team 3')
        post('/teams', newTeam)
        def member = new NewTeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: 'developer')

        when:
        def response = post('/teams/Team 3/members', member)

        then:
        response.statusCode == CREATED
    }
}

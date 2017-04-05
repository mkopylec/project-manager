package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpecification
import com.github.mkopylec.projectmanager.application.dto.NewTeam
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
        def newTeam = new NewTeam(name: 'Team 2')
        post('/teams', newTeam)

        when:
        def response = post('/teams', newTeam)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'TEAM_ALREADY_EXISTS'
    }
}

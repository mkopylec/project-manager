package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpec
import com.github.mkopylec.projectmanager.api.dto.NewTeam
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED

class TeamsSpec extends BasicSpec {

    def "Should create new team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')

        when:
        def response = post('/teams', newTeam)

        then:
        response.statusCode == CREATED
    }

    @Unroll
    def "Should not create new team with empty name"() {
        given:
        def newTeam = new NewTeam(name: name)

        when:
        def response = post('/teams', newTeam)

        then:
        response.statusCode == BAD_REQUEST
        response.body.code == 'EMPTY_TEAM_NAME'

        where:
        name << [null, '', '  ']
    }
}

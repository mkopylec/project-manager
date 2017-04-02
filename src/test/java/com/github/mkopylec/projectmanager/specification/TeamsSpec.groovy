package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpec
import com.github.mkopylec.projectmanager.api.dto.NewTeam

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
}

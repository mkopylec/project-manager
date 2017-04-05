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
    def "Should add a new member with '#jobPosition' job position to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 4')
        post('/teams', newTeam)
        def member = new NewTeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: jobPosition)

        when:
        def response = post('/teams/Team 4/members', member)

        then:
        response.statusCode == CREATED

        where:
        jobPosition << ['developer', 'DEVELOPER', 'product owner', 'Product Owner', 'product_owner', 'PRODUCT_OWNER']
    }

    @Unroll
    def "Should not add a new member without a first name to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 5')
        post('/teams', newTeam)
        def member = new NewTeamMember(firstName: firstName, lastName: 'Kopylec', jobPosition: 'developer')

        when:
        def response = post('/teams/Team 5/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_FIRST_NAME'

        where:
        firstName << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member without a last name to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 6')
        post('/teams', newTeam)
        def member = new NewTeamMember(firstName: 'Mariusz', lastName: lastName, jobPosition: 'developer')

        when:
        def response = post('/teams/Team 6/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_LAST_NAME'

        where:
        lastName << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member without job position to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 7')
        post('/teams', newTeam)
        def member = new NewTeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: jobPosition)

        when:
        def response = post('/teams/Team 7/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_JOB_POSITION'

        where:
        jobPosition << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member with '#jobPosition' job position to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 8')
        post('/teams', newTeam)
        def member = new NewTeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: jobPosition)

        when:
        def response = post('/teams/Team 8/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'INVALID_MEMBER_JOB_POSITION'

        where:
        jobPosition << ['accountant', 'Project Manager', 'TECHNICAL_LEADER']
    }

    def "Should not add a new member to a nonexistent team"() {
        given:
        def member = new NewTeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: 'developer')

        when:
        def response = post('/teams/Team 9/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'NONEXISTENT_TEAM'
    }
}

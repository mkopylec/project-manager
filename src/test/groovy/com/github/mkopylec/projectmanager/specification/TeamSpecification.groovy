package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpecification
import com.github.mkopylec.projectmanager.application.dto.NewTeam
import com.github.mkopylec.projectmanager.application.dto.TeamMember
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class TeamSpecification extends BasicSpecification {

    def "Should create new team and browse it"() {
        given:
        def newTeam1 = new NewTeam(name: 'Team 1')

        when:
        def response = post('/teams', newTeam1)

        then:
        response.statusCode == CREATED

        when:
        response = get('/teams', List)

        then:
        response.statusCode == OK
        response.body != null
        response.body.size() == 1
        with(response.body[0]) {
            name == 'Team 1'
            currentlyImplementedProjects == 0
            busy == false
            members == []
        }
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

    def "Should add a new member to a team and browse him"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: 'developer')

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == CREATED

        when:
        response = get('/teams', List)

        then:
        response.statusCode == OK
        response.body != null
        response.body.size() == 1
        with(response.body[0].members) {
            members != null
            members.size() == 1
            members[0].firstName == 'Mariusz'
            members[0].lastName == 'Kopylec'
            members[0].jobPosition == 'developer'
        }
    }

    @Unroll
    def "Should add a new member with '#jobPosition' job position to a team and browse it"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: jobPosition)

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == CREATED

        when:
        response = get('/teams', List)

        then:
        response.statusCode == OK
        response.body != null
        response.body.size() == 1
        with(response.body[0].members) {
            members != null
            members.size() == 1
            members[0].jobPosition == browsedJobPosition
        }

        where:
        jobPosition     | browsedJobPosition
        'developer'     | 'developer'
        'DEVELOPER'     | 'developer'
        'product owner' | 'product owner'
        'Product Owner' | 'product owner'
        'product_owner' | 'product owner'
        'PRODUCT_OWNER' | 'product owner'
    }

    @Unroll
    def "Should not add a new member without a first name to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: firstName, lastName: 'Kopylec', jobPosition: 'developer')

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
        def member = new TeamMember(firstName: 'Mariusz', lastName: lastName, jobPosition: 'developer')

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_LAST_NAME'

        where:
        lastName << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member without job position to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: jobPosition)

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_MEMBER_JOB_POSITION'

        where:
        jobPosition << [null, '', '  ']
    }

    @Unroll
    def "Should not add a new member with '#jobPosition' job position to a team"() {
        given:
        def newTeam = new NewTeam(name: 'Team 1')
        post('/teams', newTeam)
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: jobPosition)

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'INVALID_MEMBER_JOB_POSITION'

        where:
        jobPosition << ['accountant', 'Project Manager', 'TECHNICAL_LEADER']
    }

    def "Should not add a new member to a nonexistent team"() {
        given:
        def member = new TeamMember(firstName: 'Mariusz', lastName: 'Kopylec', jobPosition: 'developer')

        when:
        def response = post('/teams/Team 1/members', member)

        then:
        response.statusCode == NOT_FOUND
        response.body.code == 'NONEXISTENT_TEAM'
    }
}

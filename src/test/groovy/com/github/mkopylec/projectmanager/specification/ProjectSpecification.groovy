package com.github.mkopylec.projectmanager.specification

import com.github.mkopylec.projectmanager.BasicSpecification
import com.github.mkopylec.projectmanager.application.dto.NewProjectDraft
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class ProjectSpecification extends BasicSpecification {

    def "Should create new project draft"() {
        given:
        def projectDraft = new NewProjectDraft(name: 'Project 1')

        when:
        def response = post('/projects/drafts', projectDraft)

        then:
        response.statusCode == CREATED
    }

    @Unroll
    def "Should not create an unnamed new project draft"() {
        given:
        def projectDraft = new NewProjectDraft(name: name)

        when:
        def response = post('/projects/drafts', projectDraft)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'EMPTY_PROJECT_NAME'

        where:
        name << [null, '', '  ']
    }

    def "Should not create a project draft that already exists"() {
        given:
        def projectDraft = new NewProjectDraft(name: 'Project 1')
        post('/projects/drafts', projectDraft)

        when:
        def response = post('/projects/drafts', projectDraft)

        then:
        response.statusCode == UNPROCESSABLE_ENTITY
        response.body.code == 'PROJECT_ALREADY_EXISTS'
    }
}

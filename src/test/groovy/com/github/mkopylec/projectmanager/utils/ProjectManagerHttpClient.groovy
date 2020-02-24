package com.github.mkopylec.projectmanager.utils

import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft
import com.github.mkopylec.projectmanager.core.project.dto.NewProject
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft
import com.github.mkopylec.projectmanager.core.project.dto.ProjectEndingCondition
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProject
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam
import com.github.mkopylec.projectmanager.core.team.dto.NewTeamMember
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

import java.util.function.Function

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.PATCH
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpMethod.PUT

@Component
class ProjectManagerHttpClient {

    private TestRestTemplate httpClient
    private ResponseBodyReader bodyReader = new ResponseBodyReader()

    ProjectManagerHttpClient(TestRestTemplate httpClient) {
        this.httpClient = httpClient
    }

    HttpResponse<Void> createProject(NewProjectDraft newProjectDraft) {
        sendRequest('/projects/drafts', POST, newProjectDraft)
    }

    HttpResponse<Void> createProject(NewProject newProject) {
        sendRequest('/projects', POST, newProject)
    }

    HttpResponse<List<ExistingProjectDraft>> getProjects() {
        sendRequest('/projects', GET, null) { bodyReader.getExistingProjectDrafts(it) }
    }

    HttpResponse<ExistingProject> getProject(String projectIdentifier) {
        sendRequest("/projects/$projectIdentifier", GET, null) { bodyReader.getExistingProject(it) }
    }

    HttpResponse<Void> updateProject(String projectIdentifier, UpdatedProject updatedProject) {
        sendRequest("/projects/$projectIdentifier", PUT, updatedProject)
    }

    HttpResponse<Void> startProject(String projectIdentifier) {
        sendRequest("/projects/$projectIdentifier/started", PATCH, null)
    }

    HttpResponse<Void> endProject(String projectIdentifier, ProjectEndingCondition endingCondition) {
        sendRequest("/projects/$projectIdentifier/ended", PATCH, endingCondition)
    }

    HttpResponse<Void> createTeam(NewTeam newTeam) {
        sendRequest('/teams', POST, newTeam)
    }

    HttpResponse<Void> addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        sendRequest("/teams/$teamName/members", POST, newTeamMember)
    }

    HttpResponse<List<ExistingTeam>> getTeams() {
        sendRequest('/teams', GET, null) { bodyReader.getExistingTeams(it) }
    }

    private <B> HttpResponse<B> sendRequest(String uri, HttpMethod method, Object requestBody, Function<ResponseEntity<String>, B> bodyRetriever = { null }) {
        def entity = new HttpEntity<>(requestBody)
        def response = httpClient.exchange(uri, method, entity, String)
        def body = bodyRetriever.apply(response)
        def failure = bodyReader.getFailure(response)
        new HttpResponse<>(response.statusCode, body, failure)
    }
}

package com.github.mkopylec.projectmanager.utils

import com.fasterxml.jackson.databind.json.JsonMapper
import com.github.mkopylec.projectmanager.api.dto.ExistingProject
import com.github.mkopylec.projectmanager.api.dto.ExistingProjectDraft
import com.github.mkopylec.projectmanager.api.dto.ExistingTeam
import com.github.mkopylec.projectmanager.api.dto.NewProject
import com.github.mkopylec.projectmanager.api.dto.NewProjectDraft
import com.github.mkopylec.projectmanager.api.dto.NewTeam
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember
import com.github.mkopylec.projectmanager.api.dto.ProjectEndingCondition
import com.github.mkopylec.projectmanager.api.dto.UpdatedProject
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

import java.util.function.Function

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.PATCH
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpMethod.PUT
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request

@Component
class ProjectManagerHttpClient {

    private MockMvc mvc
    private ResponseBodyReader bodyReader = new ResponseBodyReader()
    private JsonMapper mapper = new JsonMapper()

    ProjectManagerHttpClient(MockMvc mvc) {
        this.mvc = mvc
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

    HttpResponse<ExistingProject> getProject(UUID projectIdentifier) {
        sendRequest("/projects/$projectIdentifier", GET, null) { bodyReader.getExistingProject(it) }
    }

    HttpResponse<Void> updateProject(UUID projectIdentifier, UpdatedProject updatedProject) {
        sendRequest("/projects/$projectIdentifier", PUT, updatedProject)
    }

    HttpResponse<Void> startProject(UUID projectIdentifier) {
        sendRequest("/projects/$projectIdentifier/started", PATCH, null)
    }

    HttpResponse<Void> endProject(UUID projectIdentifier, ProjectEndingCondition endingCondition) {
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

    private <B> HttpResponse<B> sendRequest(String uri, HttpMethod method, Object requestBody, Function<MvcResult, B> bodyRetriever = { null }) {
        def result = mvc.perform(request(method, uri)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)))
                .andReturn()
        def body = bodyRetriever.apply(result)
        def failure = bodyReader.getFailure(result)
        new HttpResponse<>(result.response.status, body, failure)
    }
}

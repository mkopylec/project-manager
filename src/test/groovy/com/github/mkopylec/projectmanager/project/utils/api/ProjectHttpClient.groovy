package com.github.mkopylec.projectmanager.project.utils.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mkopylec.projectmanager.common.utils.api.HttpClient
import com.github.mkopylec.projectmanager.common.utils.api.HttpRequest
import com.github.mkopylec.projectmanager.common.utils.api.HttpResponse
import com.github.mkopylec.projectmanager.common.utils.api.HttpResponseBodyReader
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.stereotype.Component

import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewProjectDraftBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.ProjectEndingBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.ExistingProjectBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.ExistingProjectDraftsBody
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureBody
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.PATCH
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpMethod.PUT

@Component
class ProjectHttpClient extends HttpClient {

    private static final String PROJECTS_CONTEXT_PATH = '/projects'

    private ProjectHttpClient(TestRestTemplate httpClient, HttpResponseBodyReader bodyReader, ObjectMapper mapper) {
        super(httpClient, bodyReader, mapper)
    }

    HttpResponse<Void, FailureBody> createProjectDraft(NewProjectDraftBody body) {
        def request = new HttpRequest()
                .setMethod(POST)
                .setUrl("$PROJECTS_CONTEXT_PATH/drafts")
                .setBody(body)
        sendRequest(request, Void, FailureBody)
    }

    HttpResponse<ExistingProjectDraftsBody, FailureBody> loadProjectDrafts() {
        def request = new HttpRequest()
                .setMethod(GET)
                .setUrl("$PROJECTS_CONTEXT_PATH/drafts")
        sendRequest(request, ExistingProjectDraftsBody, FailureBody)
    }

    HttpResponse<Void, FailureBody> createProject(NewProjectBody body) {
        def request = new HttpRequest()
                .setMethod(POST)
                .setUrl(PROJECTS_CONTEXT_PATH)
                .setBody(body)
        sendRequest(request, Void, FailureBody)
    }

    HttpResponse<ExistingProjectBody, FailureBody> loadProject(UUID projectId) {
        def request = new HttpRequest()
                .setMethod(GET)
                .setUrl("$PROJECTS_CONTEXT_PATH/$projectId")
        sendRequest(request, ExistingProjectBody, FailureBody)
    }

    HttpResponse<Void, FailureBody> updateProject(UUID projectId, UpdatedProjectBody body) {
        def request = new HttpRequest()
                .setMethod(PUT)
                .setUrl("$PROJECTS_CONTEXT_PATH/$projectId")
                .setBody(body)
        sendRequest(request, Void, FailureBody)
    }

    HttpResponse<Void, FailureBody> startProject(UUID projectId) {
        def request = new HttpRequest()
                .setMethod(PATCH)
                .setUrl("$PROJECTS_CONTEXT_PATH/$projectId/started")
        sendRequest(request, Void, FailureBody)
    }

    HttpResponse<Void, FailureBody> endProject(UUID projectId, ProjectEndingBody body) {
        def request = new HttpRequest()
                .setMethod(PATCH)
                .setUrl("$PROJECTS_CONTEXT_PATH/$projectId/ended")
                .setBody(body)
        sendRequest(request, Void, FailureBody)
    }
}

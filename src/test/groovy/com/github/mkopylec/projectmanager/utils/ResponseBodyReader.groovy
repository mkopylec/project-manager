package com.github.mkopylec.projectmanager.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.json.JsonMapper
import com.github.mkopylec.projectmanager.api.dto.ExistingProject
import com.github.mkopylec.projectmanager.api.dto.ExistingProjectDraft
import com.github.mkopylec.projectmanager.api.dto.ExistingTeam
import com.github.mkopylec.projectmanager.api.dto.Failure
import org.springframework.test.web.servlet.MvcResult

class ResponseBodyReader {

    private JsonMapper mapper = new JsonMapper()

    ExistingProject getExistingProject(MvcResult result) {
        getBody(result, new TypeReference<ExistingProject>() {})
    }

    List<ExistingProjectDraft> getExistingProjectDrafts(MvcResult result) {
        getBody(result, new TypeReference<List<ExistingProjectDraft>>() {})
    }

    List<ExistingTeam> getExistingTeams(MvcResult result) {
        getBody(result, new TypeReference<List<ExistingTeam>>() {})
    }

    Failure getFailure(MvcResult result) {
        getBody(result, new TypeReference<Failure>() {})
    }

    private <B> B getBody(MvcResult result, TypeReference<B> type) {
        try {
            mapper.readValue(result.response.contentAsString, type)
        } catch (Exception e) {
            null
        }
    }
}

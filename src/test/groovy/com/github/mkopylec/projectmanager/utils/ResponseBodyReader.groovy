package com.github.mkopylec.projectmanager.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.json.JsonMapper
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam
import com.github.mkopylec.projectmanager.presentation.Failure
import org.springframework.http.ResponseEntity

class ResponseBodyReader {

    private JsonMapper mapper = new JsonMapper()

    ExistingProject getExistingProject(ResponseEntity<String> response) {
        getBody(response, new TypeReference<ExistingProject>() {})
    }

    List<ExistingProjectDraft> getExistingProjectDrafts(ResponseEntity<String> response) {
        getBody(response, new TypeReference<List<ExistingProjectDraft>>() {})
    }

    List<ExistingTeam> getExistingTeams(ResponseEntity<String> response) {
        getBody(response, new TypeReference<List<ExistingTeam>>() {})
    }

    Failure getFailure(ResponseEntity<String> response) {
        getBody(response, new TypeReference<Failure>() {})
    }

    private <B> B getBody(ResponseEntity<String> response, TypeReference<B> type) {
        try {
            mapper.readValue(response.body, type)
        } catch (Exception e) {
            null
        }
    }
}

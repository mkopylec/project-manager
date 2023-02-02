package com.github.mkopylec.projectmanager.common.utils.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class HttpResponseBodyReader {

    private ObjectMapper jsonMapper

    protected HttpResponseBodyReader(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper
    }

    protected <B> B getBody(ResponseEntity<String> response, Class<B> bodyType) {
        try {
            jsonMapper.readValue(response.body, bodyType)
        } catch (Exception ignored) {
            null
        }
    }
}

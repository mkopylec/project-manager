package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.api.dto.NewTeam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/teams")
public interface TeamsEndpoint {

    @ResponseStatus(CREATED)
    @PostMapping
    void createTeam(NewTeam newTeam);
}

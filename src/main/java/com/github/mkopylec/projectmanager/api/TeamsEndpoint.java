package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.api.dto.NewTeam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/teams")
public interface TeamsEndpoint {

    @PostMapping
    void createTeam(NewTeam newTeam);
}

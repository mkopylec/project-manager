package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.application.dto.NewTeam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/teams")
class TeamsController {

    @ResponseStatus(CREATED)
    @PostMapping
    public void createTeam(@RequestBody NewTeam newTeam) {

    }
}

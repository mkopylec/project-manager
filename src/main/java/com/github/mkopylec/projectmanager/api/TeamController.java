package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.api.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.api.dto.NewTeam;
import com.github.mkopylec.projectmanager.api.dto.NewTeamMember;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequestMapping(path = "/teams")
class TeamController {

    private ProjectManager projectManager;

    TeamController(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void createTeam(@Valid @RequestBody NewTeam newTeam) {
        projectManager.createTeam(newTeam);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{teamName}/members")
    void addMemberToTeam(@PathVariable String teamName, @Validated @RequestBody NewTeamMember newTeamMember) {
        projectManager.addMemberToTeam(teamName, newTeamMember);
    }

    @ResponseStatus(OK)
    @GetMapping
    List<ExistingTeam> getTeams() {
        return projectManager.getTeams();
    }
}

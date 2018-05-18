package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.application.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.application.dto.NewTeam;
import com.github.mkopylec.projectmanager.application.dto.TeamMember;
import com.github.mkopylec.projectmanager.team.TeamApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/teams")
class TeamController {

    private TeamApi teamApi;

    TeamController(TeamApi teamApi) {
        this.teamApi = teamApi;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public void createTeam(@RequestBody NewTeam newTeam) {
        teamApi.createTeam(newTeam);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{teamName}/members")
    public void addMemberToTeam(@PathVariable String teamName, @RequestBody TeamMember teamMember) {
        teamApi.addMemberToTeam(teamName, teamMember);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<ExistingTeam> getTeams() {
        return teamApi.getTeams();
    }
}

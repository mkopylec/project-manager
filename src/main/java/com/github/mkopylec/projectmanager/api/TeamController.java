package com.github.mkopylec.projectmanager.api;

import java.util.List;

import com.github.mkopylec.projectmanager.application.TeamService;
import com.github.mkopylec.projectmanager.application.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.application.dto.NewTeam;
import com.github.mkopylec.projectmanager.application.dto.TeamMember;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/teams")
class TeamController {

    private TeamService teamService;

    TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public void createTeam(@RequestBody NewTeam newTeam) {
        teamService.createTeam(newTeam);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{teamName}/members")
    public void addMemberToTeam(@PathVariable String teamName, @RequestBody TeamMember teamMember) {
        teamService.addMemberToTeam(teamName, teamMember);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<ExistingTeam> getTeams() {
        return teamService.getTeams();
    }
}

package com.github.mkopylec.projectmanager.presentation;

import com.github.mkopylec.projectmanager.core.team.TeamService;
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeamMember;
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

/**
 * Primary adapter
 */
@RestController
@RequestMapping("/teams")
class TeamController {

    private TeamService teamService;

    TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void createTeam(@RequestBody NewTeam newTeam) {
        teamService.createTeam(newTeam);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{teamName}/members")
    void addMemberToTeam(@PathVariable String teamName, @RequestBody NewTeamMember newTeamMember) {
        teamService.addMemberToTeam(new NewTeamMember(teamName, newTeamMember.getFirstName(), newTeamMember.getLastName(), newTeamMember.getJobPosition()));
    }

    @ResponseStatus(OK)
    @GetMapping
    List<ExistingTeam> getTeams() {
        return teamService.getTeams();
    }
}

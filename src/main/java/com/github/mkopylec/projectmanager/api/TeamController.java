package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.core.ProjectManager;
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam;
import com.github.mkopylec.projectmanager.core.team.dto.TeamMember;
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

    private ProjectManager projectManager;

    TeamController(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void createTeam(@RequestBody NewTeam newTeam) {
        projectManager.createTeam(newTeam);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{teamName}/members")
    void addMemberToTeam(@PathVariable String teamName, @RequestBody TeamMember teamMember) {
        projectManager.addMemberToTeam(teamName, teamMember);
    }

    @ResponseStatus(OK)
    @GetMapping
    List<ExistingTeam> getTeams() {
        return projectManager.getTeams();
    }
}

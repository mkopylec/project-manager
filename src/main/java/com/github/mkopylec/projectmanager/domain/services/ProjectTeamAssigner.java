package com.github.mkopylec.projectmanager.domain.services;

import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.team.Team;

import org.springframework.stereotype.Service;

@Service
public class ProjectTeamAssigner {

    public void assignTeamToProject(Team team, Project project) {
        project.assignTeam(team);
        if (team != null) {
            team.addCurrentlyImplementedProject();
        }
    }
}

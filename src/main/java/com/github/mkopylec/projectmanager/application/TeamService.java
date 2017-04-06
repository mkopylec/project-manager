package com.github.mkopylec.projectmanager.application;

import com.github.mkopylec.projectmanager.application.dto.NewTeam;
import com.github.mkopylec.projectmanager.application.dto.NewTeamMember;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.team.TeamFactory;
import com.github.mkopylec.projectmanager.domain.team.TeamRepository;
import com.github.mkopylec.projectmanager.domain.values.Employee;
import com.github.mkopylec.projectmanager.domain.values.JobPosition;

import org.springframework.stereotype.Service;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.INVALID_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.NONEXISTENT_TEAM;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.TEAM_ALREADY_EXISTS;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static com.github.mkopylec.projectmanager.domain.values.JobPosition.createJobPosition;

@Service
public class TeamService {

    private final TeamFactory teamFactory;
    private final TeamRepository teamRepository;

    public TeamService(TeamFactory teamFactory, TeamRepository teamRepository) {
        this.teamFactory = teamFactory;
        this.teamRepository = teamRepository;
    }

    public void createTeam(NewTeam newTeam) {
        Team team = teamFactory.createTeam(newTeam.getName());
        when(teamRepository.existsByName(team.getName()))
                .thenEntityAlreadyExists(TEAM_ALREADY_EXISTS, "Error creating team named '" + team.getName() + "'");
        teamRepository.save(team);
    }

    public void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        String position = newTeamMember.getJobPosition();
        JobPosition jobPosition = createJobPosition(position);
        when(jobPosition == null)
                .thenInvalidValue(INVALID_MEMBER_JOB_POSITION, "Error adding member with '" + position + "' job position to '" + teamName + "' team");
        Team team = teamRepository.findByName(teamName);
        when(team == null)
                .thenMissingEntity(NONEXISTENT_TEAM, "Error adding member to '" + teamName + "' team");
        Employee member = new Employee(newTeamMember.getFirstName(), newTeamMember.getLastName(), jobPosition);
        team.addMember(member);
    }
}

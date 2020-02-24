package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.UseCaseService;
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeam;
import com.github.mkopylec.projectmanager.core.team.dto.NewTeamMember;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.team.TeamRequirementsValidator.teamRequirements;

public class TeamService extends UseCaseService {

    private OutgoingDtoMapper dtoMapper = new OutgoingDtoMapper();
    private TeamFactory factory = new TeamFactory();
    private TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public void createTeam(NewTeam newTeam) {
        executeUseCase("create team", newTeam, () -> {
            var existingTeam = repository.findByName(newTeam.getName());
            teamRequirements()
                    .requireNoTeam(existingTeam)
                    .validate();
            var team = factory.createTeam(newTeam);
            repository.save(team);
        });
    }

    public void addMemberToTeam(NewTeamMember newTeamMember) {
        executeUseCase("add member to team", newTeamMember, () -> {
            var team = repository.findByName(newTeamMember.getTeamName());
            teamRequirements()
                    .requireTeam(team)
                    .validate();
            var member = factory.createMember(newTeamMember);
            team.addMember(member);
            repository.save(team);
        });
    }

    public List<ExistingTeam> getTeams() {
        return executeUseCase("get teams", () -> {
            var teams = repository.findAll();
            return dtoMapper.mapToExistingTeams(teams);
        });
    }
}

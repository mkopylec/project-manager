package com.github.mkopylec.projectmanager.team.core;

import com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation;
import com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties;

import static com.github.mkopylec.projectmanager.team.core.BusyTeamThreshold.InvalidBusyTeamThreshold;
import static com.github.mkopylec.projectmanager.team.core.JobPosition.InvalidJobPosition;
import static com.github.mkopylec.projectmanager.team.core.MemberFirstName.InvalidEmployeeFirstName;
import static com.github.mkopylec.projectmanager.team.core.MemberLastName.InvalidEmployeeLastName;
import static com.github.mkopylec.projectmanager.team.core.TeamCurrentlyImplementedProjects.InvalidTeamCurrentlyImplementedProjects;
import static com.github.mkopylec.projectmanager.team.core.TeamName.InvalidTeamName;

abstract sealed class TeamBusinessRuleViolation extends BusinessRuleViolation permits InvalidBusyTeamThreshold, InvalidJobPosition, InvalidEmployeeFirstName, InvalidEmployeeLastName, InvalidTeamCurrentlyImplementedProjects, InvalidTeamName {

    TeamBusinessRuleViolation(BusinessRuleViolationProperties properties) {
        super(properties);
    }

    TeamBusinessRuleViolation(BusinessRuleViolationProperties properties, Exception cause) {
        super(properties, cause);
    }
}

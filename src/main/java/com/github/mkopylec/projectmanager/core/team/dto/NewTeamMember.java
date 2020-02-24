package com.github.mkopylec.projectmanager.core.team.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class NewTeamMember {

    private String teamName;
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    public NewTeamMember(String teamName, String firstName, String lastName, JobPosition jobPosition) {
        this.teamName = teamName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = jobPosition;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("teamName", teamName)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("jobPosition", jobPosition)
                .toString();
    }

    private NewTeamMember() {
    }
}

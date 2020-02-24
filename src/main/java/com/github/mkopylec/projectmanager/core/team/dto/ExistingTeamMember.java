package com.github.mkopylec.projectmanager.core.team.dto;

public class ExistingTeamMember {

    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    public ExistingTeamMember(String firstName, String lastName, JobPosition jobPosition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = jobPosition;
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

    private ExistingTeamMember() {
    }
}

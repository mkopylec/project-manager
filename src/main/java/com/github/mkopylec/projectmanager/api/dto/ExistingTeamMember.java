package com.github.mkopylec.projectmanager.api.dto;

public class ExistingTeamMember {

    private String firstName;
    private String lastName;
    private String jobPosition;

    public String getFirstName() {
        return firstName;
    }

    public ExistingTeamMember setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ExistingTeamMember setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public ExistingTeamMember setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
        return this;
    }
}

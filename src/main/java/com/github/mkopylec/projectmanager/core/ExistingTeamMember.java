package com.github.mkopylec.projectmanager.core;

public class ExistingTeamMember {

    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    ExistingTeamMember() {
    }

    public String getFirstName() {
        return firstName;
    }

    ExistingTeamMember setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    ExistingTeamMember setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    ExistingTeamMember setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
        return this;
    }
}

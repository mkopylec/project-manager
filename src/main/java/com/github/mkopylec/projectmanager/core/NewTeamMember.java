package com.github.mkopylec.projectmanager.core;

public class NewTeamMember {

    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    public NewTeamMember(String firstName, String lastName, JobPosition jobPosition) {
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

    private NewTeamMember() {
    }
}

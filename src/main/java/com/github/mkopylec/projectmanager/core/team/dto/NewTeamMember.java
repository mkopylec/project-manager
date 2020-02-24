package com.github.mkopylec.projectmanager.core.team.dto;

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

    private NewTeamMember() {
    }
}

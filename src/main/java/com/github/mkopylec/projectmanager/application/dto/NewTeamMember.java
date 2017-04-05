package com.github.mkopylec.projectmanager.application.dto;

import com.github.mkopylec.projectmanager.domain.values.JobPosition;

public class NewTeamMember {

    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }
}

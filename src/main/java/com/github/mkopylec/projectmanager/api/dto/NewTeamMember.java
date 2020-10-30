package com.github.mkopylec.projectmanager.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NewTeamMember {

    @NotBlank(message = "EMPTY_NEW_TEAM_MEMBER_FIRST_NAME")
    private String firstName;
    @NotBlank(message = "EMPTY_NEW_TEAM_MEMBER_LAST_NAME")
    private String lastName;
    @NotBlank(message = "EMPTY_NEW_TEAM_MEMBER_JOB_POSITION")
    @Pattern(message = "INVALID_NEW_TEAM_MEMBER_JOB_POSITION", regexp = "^(\\s*|DEVELOPER|SCRUM_MASTER|PRODUCT_OWNER)$")
    private String jobPosition;

    public NewTeamMember(String firstName, String lastName, String jobPosition) {
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

    public String getJobPosition() {
        return jobPosition;
    }

    private NewTeamMember() {
    }
}

package com.github.mkopylec.projectmanager.core.team;

import org.springframework.data.annotation.PersistenceConstructor;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isNotEmpty;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.EMPTY_MEMBER_FIRST_NAME;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.EMPTY_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.core.team.TeamViolation.EMPTY_MEMBER_LAST_NAME;

public class Member {

    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    @PersistenceConstructor
    Member(String firstName, String lastName, JobPosition jobPosition) {
        require(isNotEmpty(firstName), EMPTY_MEMBER_FIRST_NAME);
        require(isNotEmpty(lastName), EMPTY_MEMBER_LAST_NAME);
        require(isNotEmpty(jobPosition), EMPTY_MEMBER_JOB_POSITION);
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
}

package com.github.mkopylec.projectmanager.team.core;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static java.util.Objects.requireNonNull;

public class Member {

    private MemberFirstName firstName;
    private MemberLastName lastName;
    private JobPosition jobPosition;

    Member(MemberFirstName firstName, MemberLastName lastName, JobPosition jobPosition) {
        setFirstName(firstName);
        setLastName(lastName);
        setJobPosition(jobPosition);
    }

    public static Member fromPersistentState(MemberFirstName firstName, MemberLastName lastName, JobPosition jobPosition) {
        return requireNoBusinessRuleViolation(() -> new Member(firstName, lastName, jobPosition));
    }

    public MemberFirstName getFirstName() {
        return firstName;
    }

    public MemberLastName getLastName() {
        return lastName;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    private void setFirstName(MemberFirstName firstName) {
        this.firstName = requireNonNull(firstName, "No member first name");
    }

    private void setLastName(MemberLastName lastName) {
        this.lastName = requireNonNull(lastName, "No member last name");
    }

    private void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = requireNonNull(jobPosition, "No member job position");
    }
}

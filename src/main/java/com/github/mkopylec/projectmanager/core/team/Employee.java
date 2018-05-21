package com.github.mkopylec.projectmanager.core.team;

import static org.apache.commons.lang3.StringUtils.isBlank;

class Employee {

    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    Employee(String firstName, String lastName, JobPosition jobPosition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = jobPosition;
    }

    String getFirstName() {
        return firstName;
    }

    boolean hasNoFirstName() {
        return isBlank(firstName);
    }

    String getLastName() {
        return lastName;
    }

    boolean hasNoLastName() {
        return isBlank(lastName);
    }

    JobPosition getJobPosition() {
        return jobPosition;
    }

    boolean hasNoJobPosition() {
        return !hasJobPosition();
    }

    boolean hasInvalidJobPosition() {
        return hasJobPosition() && jobPosition.isInvalid();
    }

    private boolean hasJobPosition() {
        return jobPosition != null;
    }

    private Employee() {
    }
}

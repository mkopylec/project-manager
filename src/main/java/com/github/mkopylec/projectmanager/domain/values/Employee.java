package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.JobPosition._INVALID;
import static com.github.mkopylec.projectmanager.domain.values.JobPosition.createJobPosition;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Employee {

    private final String firstName;
    private final String lastName;
    private final JobPosition jobPosition;

    private Employee(String firstName, String lastName, JobPosition jobPosition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = jobPosition;
    }

    public static Employee createEmployee(String firstName, String lastName, String jobPosition) {
        return new Employee(firstName, lastName, createJobPosition(jobPosition));
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean hasNoFirstName() {
        return isBlank(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public boolean hasNoLastName() {
        return isBlank(lastName);
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public boolean hasNoJobPosition() {
        return jobPosition == null;
    }

    public boolean hasInvalidJobPosition() {
        return jobPosition == _INVALID;
    }
}

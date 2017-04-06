package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Employee {

    private final String firstName;
    private final String lastName;
    private final JobPosition jobPosition;

    public Employee(String firstName, String lastName, JobPosition jobPosition) {
        when(isBlank(firstName)).
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

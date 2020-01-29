package com.github.mkopylec.projectmanager.core.team;

import static com.github.mkopylec.projectmanager.core.common.Utilities.allEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;

public class Employee {

    private String firstName;
    private String lastName;
    private JobPosition jobPosition;

    static Employee employee(String firstName, String lastName, JobPosition jobPosition) {
        return allEmpty(firstName, lastName, jobPosition) ? null : new Employee(firstName, lastName, jobPosition);
    }

    private Employee(String firstName, String lastName, JobPosition jobPosition) {
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

    boolean hasNoFirstName() {
        return isEmpty(firstName);
    }

    boolean hasNoLastName() {
        return isEmpty(lastName);
    }

    boolean hasNoJobPosition() {
        return isEmpty(jobPosition);
    }

    public static class EmployeePersistenceFactory {

        public Employee createEmployee(String firstName, String lastName, JobPosition jobPosition) {
            return allEmpty(firstName, lastName, jobPosition) ? null : new Employee(firstName, lastName, jobPosition);
        }
    }
}

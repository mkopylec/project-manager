package com.github.mkopylec.projectmanager.infrastructure.persistence;

class EmployeeDocument {

    private String firstName;
    private String lastName;
    private String jobPosition;

    String getFirstName() {
        return firstName;
    }

    EmployeeDocument setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    String getLastName() {
        return lastName;
    }

    EmployeeDocument setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    String getJobPosition() {
        return jobPosition;
    }

    EmployeeDocument setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
        return this;
    }
}

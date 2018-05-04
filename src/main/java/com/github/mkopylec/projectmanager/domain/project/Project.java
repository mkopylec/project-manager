package com.github.mkopylec.projectmanager.domain.project;

import com.github.mkopylec.projectmanager.domain.values.Status;
import org.springframework.data.annotation.Id;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static com.github.mkopylec.projectmanager.domain.values.Status.TO_DO;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Project {

    @Id
    private String identifier;
    private String name;
    private Status status;
    private String assignedTeam;

    Project(String identifier, String name) {
        validateIdentifier(identifier, "Error creating '" + name + "' project");
        validateName(name, "Error creating '" + identifier + "' project");
        this.identifier = identifier;
        this.name = name;
        this.status = TO_DO;
    }

    private void validateIdentifier(String identifier, String message) {
        when(isBlank(identifier))
                .thenInvalidEntity(EMPTY_PROJECT_IDENTIFIER, message);
    }

    private void validateName(String name, String message) {
        when(isBlank(name))
                .thenInvalidEntity(EMPTY_PROJECT_NAME, message);
    }

    private Project() {
    }
}

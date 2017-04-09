package com.github.mkopylec.projectmanager.domain.project;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_STATUS;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Project {

    private String identifier;
    private String name;
    private Status status;

    Project(String identifier, String name, Status status) {
        when(isBlank(identifier)).thenInvalidEntity(EMPTY_PROJECT_IDENTIFIER, "Error creating '" + name + "'project");
        when(isBlank(name)).thenInvalidEntity(EMPTY_PROJECT_NAME, "Error creating '" + identifier + "'project");
        when(status == null).thenInvalidEntity(EMPTY_PROJECT_STATUS, "Error creating '" + name + "'project");
        this.identifier = identifier;
        this.name = name;
        this.status = status;
    }
}

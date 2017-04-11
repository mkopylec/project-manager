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

    Project(String identifier, String name) {
        when(isBlank(identifier))
                .thenInvalidEntity(EMPTY_PROJECT_IDENTIFIER, "Error creating '" + name + "'project");
        when(isBlank(name))
                .thenInvalidEntity(EMPTY_PROJECT_NAME, "Error creating '" + identifier + "'project");
        this.identifier = identifier;
        this.name = name;
        this.status = TO_DO;
    }

    private Project() {
    }
}

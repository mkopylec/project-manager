package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.ProjectManagerException;
import com.github.mkopylec.projectmanager.core.common.ValidationError;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ErrorCode.MISSING_PROJECT;

public class ProjectException extends ProjectManagerException {

    ProjectException(List<ValidationError> validationErrors) {
        super(MISSING_PROJECT, validationErrors);
    }
}

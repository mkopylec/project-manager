package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;
import com.github.mkopylec.projectmanager.core.common.ValidationException;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.MISSING_PROJECT;

class ProjectException extends ValidationException {

    ProjectException(List<? extends Enum<? extends ValidationErrorCode>> errorCodes) {
        super(errorCodes);
    }

    @Override
    protected boolean indicatesMissingEntity() {
        return containsErrorCode(MISSING_PROJECT);
    }
}

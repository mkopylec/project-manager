package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.RequirementsValidationException;
import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;

import java.util.List;

class ProjectException extends RequirementsValidationException {

    ProjectException(List<Enum<? extends ValidationErrorCode>> errorCodes) {
        super(errorCodes);
    }
}

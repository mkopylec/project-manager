package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.RequirementsValidationException;
import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;

import java.util.List;

public class InputDataException extends RequirementsValidationException {

    InputDataException(List<Enum<? extends ValidationErrorCode>> errorCodes) {
        super(errorCodes);
    }
}

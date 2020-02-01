package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.RequirementsValidator;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;

class InputDataRequirementsValidator extends RequirementsValidator {

    static InputDataRequirementsValidator requirements() {
        return new InputDataRequirementsValidator();
    }

    private InputDataRequirementsValidator() {
        super(InputDataException::new);
    }

    InputDataRequirementsValidator require(Object inputData, ErrorCode errorCode) {
        if (isEmpty(inputData)) {
            addError(errorCode);
        }
        return this;
    }
}

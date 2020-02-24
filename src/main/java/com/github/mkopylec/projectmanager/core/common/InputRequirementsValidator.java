package com.github.mkopylec.projectmanager.core.common;

import static com.github.mkopylec.projectmanager.core.common.InputErrorCode.EMPTY_INPUT;

class InputRequirementsValidator extends Validator<InputErrorCode> {

    static InputRequirementsValidator requirements() {
        return new InputRequirementsValidator();
    }

    private InputRequirementsValidator() {
        super(InputException::new);
    }

    InputRequirementsValidator require(Object input) {
        return require(input, EMPTY_INPUT);
    }
}

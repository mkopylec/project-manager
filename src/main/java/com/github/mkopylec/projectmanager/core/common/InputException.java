package com.github.mkopylec.projectmanager.core.common;

import java.util.List;

class InputException extends ValidationException {

    InputException(List<InputErrorCode> errorCodes) {
        super(errorCodes);
    }

    @Override
    protected boolean indicatesMissingEntity() {
        return false;
    }
}

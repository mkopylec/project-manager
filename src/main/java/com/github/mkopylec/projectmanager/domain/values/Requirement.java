package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.EnumCreator.createEnum;

public enum Requirement {

    _INVALID,
    OPTIONAL,
    RECOMMENDED,
    NECESSARY;

    public static Requirement createRequirement(String requirement) {
        return createEnum(Requirement.class, requirement, _INVALID);
    }
}

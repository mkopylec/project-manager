package com.github.mkopylec.projectmanager.utils

import com.github.mkopylec.projectmanager.core.project.EndedProject

class EndedProjectBuilder {

    UUID projectIdentifier

    EndedProject build() {
        new EndedProject(projectIdentifier)
    }
}

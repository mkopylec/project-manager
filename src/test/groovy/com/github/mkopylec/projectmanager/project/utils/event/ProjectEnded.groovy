package com.github.mkopylec.projectmanager.project.utils.event

import com.github.mkopylec.projectmanager.common.utils.event.JsonEventPayload

class ProjectEnded extends JsonEventPayload {

    UUID projectId
}

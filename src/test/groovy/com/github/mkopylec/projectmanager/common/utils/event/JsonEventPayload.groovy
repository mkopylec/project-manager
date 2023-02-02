package com.github.mkopylec.projectmanager.common.utils.event

import java.time.Instant

abstract class JsonEventPayload {

    UUID eventId;
    Instant occurrenceDate;
}

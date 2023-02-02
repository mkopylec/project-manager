package com.github.mkopylec.projectmanager.common.utils.time

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

import static java.time.Duration.ZERO

@Primary
@Component
class MovableClock extends Clock {

    private Duration currentOffset
    private Clock clock
    private Instant fixedNow

    MovableClock() {
        reset()
    }

    void moveForward(Duration duration) {
        currentOffset += duration
        applyOffset()
    }

    void moveBackward(Duration duration) {
        currentOffset -= duration
        applyOffset()
    }

    void stop() {
        fixedNow = clock.instant()
    }

    void resume() {
        fixedNow = null
    }

    void reset() {
        currentOffset = ZERO
        fixedNow = null
        applyOffset()
    }

    @Override
    ZoneId getZone() {
        clock.zone
    }

    @Override
    Clock withZone(ZoneId zone) {
        clock.withZone(zone)
    }

    @Override
    Instant instant() {
        fixedNow ? fixedNow : clock.instant()
    }

    private void applyOffset() {
        clock = offset(systemUTC(), currentOffset)
        if (fixedNow) {
            fixedNow += currentOffset
        }
    }
}

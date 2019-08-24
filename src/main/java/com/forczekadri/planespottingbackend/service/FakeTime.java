package com.forczekadri.planespottingbackend.service;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component // singleton
public class FakeTime extends Clock {

    private ZonedDateTime begin;

    public FakeTime() {
        this.begin = ZonedDateTime.now();
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return null;
    }

    @Override
    public Instant instant() {
        ZonedDateTime now = ZonedDateTime.now();
        long nowSeconds = now.toInstant().getEpochSecond();
        long beginSeconds = begin.toInstant().getEpochSecond();
        // Difference between the starting point of this clock and 'now'.
        long delta = nowSeconds - beginSeconds;
        // Creates a datetime that is in the future.
        return begin.plusSeconds(delta * 10).toInstant();
    }
}

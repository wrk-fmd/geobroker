/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.util.Positions;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class InMemoryPositionRepositoryTest {
    private InMemoryPositionRepository sut;
    private Instant fixedInstant;

    @Before
    public void init() {
        fixedInstant = Instant.now();
        sut = new InMemoryPositionRepository(Clock.fixed(fixedInstant, ZoneId.systemDefault()));
    }

    @Test
    public void storePosition_positionCanBeRetrieved() {
        Position position = Positions.createPosition();
        String unitId = "unit id";

        sut.storePosition(unitId, position);

        Optional<Position> storedPosition = sut.getPosition(unitId);
        assertThat(storedPosition, hasValue(equalTo(position)));
    }

    @Test
    public void positionIsUpdatedWithNewerTimestamp_newPositionIsReturned() {
        Instant timestamp = Instant.now();
        Position position = Positions.createPosition(timestamp);
        Position updatedPosition = Positions.createPosition(timestamp.plusSeconds(10));
        String unitId = "unit id";

        sut.storePosition(unitId, position);
        sut.storePosition(unitId, updatedPosition);

        Optional<Position> storedPosition = sut.getPosition(unitId);
        assertThat("Newer position update shall be stored.", storedPosition, hasValue(equalTo(updatedPosition)));
    }

    @Test
    public void positionIsUpdatedWithOlderTimestamp_newPositionIsReturned() {
        Instant timestamp = Instant.now();
        Position position = Positions.createPosition(timestamp);
        Position updatedPosition = Positions.createPosition(timestamp.minusSeconds(10));
        String unitId = "unit id";

        sut.storePosition(unitId, position);
        sut.storePosition(unitId, updatedPosition);

        Optional<Position> storedPosition = sut.getPosition(unitId);
        assertThat("Outdated position update shall NOT be stored.", storedPosition, hasValue(equalTo(position)));
    }

    @Test
    public void cleanupOutdatedPositions_olderPositionIsDeleted() {
        Instant timestamp = Instant.now();
        Position position = Positions.createPosition(timestamp);

        String unitId = "a unit id";
        sut.storePosition(unitId, position);
        // Store multiple units to test concurency concern
        sut.storePosition("another unit", Positions.createPosition(timestamp));
        sut.storePosition("third unit", Positions.createPosition(timestamp.plusSeconds(20)));

        sut.cleanupOutdatedPositions(timestamp.plusSeconds(10));

        Optional<Position> storedPosition = sut.getPosition(unitId);
        assertThat(storedPosition, isEmpty());
    }

    @Test
    public void cleanupOutdatedPositions_newerPositionIsKept() {
        Instant timestamp = Instant.now();
        Position position = Positions.createPosition(timestamp);

        String unitId = "a unit id";
        sut.storePosition(unitId, position);

        sut.cleanupOutdatedPositions(timestamp.minusSeconds(10));

        Optional<Position> storedPosition = sut.getPosition(unitId);
        assertThat(storedPosition, isPresent());
    }

    @Test
    public void positionIsOutdated_noPositionReturned() {
        Position position = Positions.createPosition(fixedInstant.minus(15, ChronoUnit.MINUTES));
        String unitId = "unit id";

        sut.storePosition(unitId, position);

        Optional<Position> storedPosition = sut.getPosition(unitId, 10);
        assertThat(storedPosition, isEmpty());
    }
}
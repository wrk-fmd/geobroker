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

import java.time.Instant;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class InMemoryPositionRepositoryTest {
    private InMemoryPositionRepository sut;

    @Before
    public void init() {
        sut = new InMemoryPositionRepository();
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
}
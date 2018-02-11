/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.position;

import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.repository.PositionRepository;
import at.wrk.fmd.geobroker.util.Positions;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static at.wrk.fmd.geobroker.service.position.PositionUpdateResult.NOT_ALLOWED;
import static at.wrk.fmd.geobroker.service.position.PositionUpdateResult.UPDATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SimplePositionServiceTest {
    private SimplePositionService sut;
    private UnitRepository unitRepository;
    private PositionRepository positionRepository;
    private Instant fixedInstant;

    @Before
    public void init() {
        unitRepository = mock(UnitRepository.class);
        positionRepository = mock(PositionRepository.class);
        fixedInstant = Instant.now();
        sut = new SimplePositionService(unitRepository, positionRepository, true, Clock.fixed(fixedInstant, ZoneOffset.UTC));
    }

    @Test
    public void tokenIsNotAuthorized_returnNotAllowed() {
        when(unitRepository.isTokenAuthorized(any(), any())).thenReturn(false);

        PositionUpdateResult result = sut.updatePosition("unit 1", "unauthorized token", Positions.createPosition());

        assertThat(result, equalTo(NOT_ALLOWED));
    }

    @Test
    public void tokenIsAuthorized_returnUpdated() {
        String unitId = "unit 1";
        String token = "authorized token";
        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);

        PositionUpdateResult result = sut.updatePosition(unitId, token, Positions.createPosition());

        assertThat(result, equalTo(UPDATED));
    }

    @Test
    public void tokenIsAuthorized_positionIsUpdated() {
        String unitId = "unit 1";
        String token = "authorized token";
        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);

        Position position = Positions.createPosition(fixedInstant.minusSeconds(15));
        sut.updatePosition(unitId, token, position);

        verify(positionRepository).storePosition(unitId, position);
    }

    @Test
    public void timestampIsInTheFuture_useCurrentTime() {
        String unitId = "unit 1";
        String token = "authorized token";
        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);

        Position position = Positions.createPosition(fixedInstant.plusSeconds(15));
        sut.updatePosition(unitId, token, position);

        verify(positionRepository).storePosition(eq(unitId), argThat(samePropertyValuesAs(Positions.createPosition(fixedInstant))));
    }

    @Test
    public void configuredToUseServerTime_useCurrentTime() {
        sut = new SimplePositionService(unitRepository, positionRepository, false, Clock.fixed(fixedInstant, ZoneOffset.UTC));

        String unitId = "unit 1";
        String token = "authorized token";
        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);

        Position position = Positions.createPosition(fixedInstant.minusSeconds(15));
        sut.updatePosition(unitId, token, position);

        verify(positionRepository).storePosition(eq(unitId), argThat(samePropertyValuesAs(Positions.createPosition(fixedInstant))));
    }
}
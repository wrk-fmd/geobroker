/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.generic.Point;
import at.wrk.fmd.geobroker.contract.poi.GetAllPoisResponse;
import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;
import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.repository.IncidentRepository;
import at.wrk.fmd.geobroker.repository.PoiRepository;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import at.wrk.fmd.geobroker.util.Positions;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleScopeServiceTest {

    private SimpleScopeService sut;
    private UnitRepository unitRepository;
    private IncidentRepository incidentRepository;
    private PoiRepository poiRepository;

    @Before
    public void init() {
        LiveUnitMapper mapper = mock(LiveUnitMapper.class);
        unitRepository = mock(UnitRepository.class);
        incidentRepository = mock(IncidentRepository.class);
        poiRepository = mock(PoiRepository.class);
        sut = new SimpleScopeService(unitRepository, incidentRepository, poiRepository, mapper);
    }

    @Test
    public void tokenNotAuthorized_returnEmptyScopeOptional() {
        when(unitRepository.isTokenAuthorized(any(), any())).thenReturn(false);

        Optional<ScopeResponse> scopeForUnit = sut.getScopeForUnit("unit id", "token", 42);

        assertThat(scopeForUnit, isEmpty());
    }

    @Test
    public void tokenNotAuthorized_returnEmptyPoiOptional() {
        when(unitRepository.isTokenAuthorized(any(), any())).thenReturn(false);

        Optional<GetAllPoisResponse> poisForUnit = sut.getPoisForUnit("unit id", "token");

        assertThat(poisForUnit, isEmpty());
    }

    @Test
    public void tokenAuthorized_returnScope() {
        String unitId = "unit id";
        String token = "token";
        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);
        ImmutableSet<PointOfInterest> configuredPois = ImmutableSet.of(new PointOfInterest("poi", "type1", "poi info", Positions.random()));
        when(poiRepository.getAll()).thenReturn(configuredPois);

        Optional<GetAllPoisResponse> response = sut.getPoisForUnit(unitId, token);

        assertThat(response, hasValue(samePropertyValuesAs(new GetAllPoisResponse(configuredPois))));
    }
}

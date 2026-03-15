/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.generic.OneTimeAction;
import at.wrk.fmd.geobroker.contract.incident.DispatchIncident;
import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.contract.poi.GetAllPoisResponse;
import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;
import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import at.wrk.fmd.geobroker.repository.IncidentRepository;
import at.wrk.fmd.geobroker.repository.PoiRepository;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleScopeServiceTest {

    private SimpleScopeService sut;
    private UnitRepository unitRepository;
    private IncidentRepository incidentRepository;
    private PoiRepository poiRepository;
    private LiveUnitMapper mapper;
    private DispatchIncidentMapper dispatchIncidentMapper;

    @BeforeEach
    void init() {
        mapper = mock(LiveUnitMapper.class);
        dispatchIncidentMapper = mock(DispatchIncidentMapper.class);
        unitRepository = mock(UnitRepository.class);
        incidentRepository = mock(IncidentRepository.class);
        poiRepository = mock(PoiRepository.class);
        sut = new SimpleScopeService(unitRepository, incidentRepository, poiRepository, mapper, dispatchIncidentMapper);
    }

    @Test
    void tokenNotAuthorized_returnEmptyScopeOptional() {
        when(unitRepository.isTokenAuthorized(any(), any())).thenReturn(false);

        Optional<ScopeResponse> scopeForUnit = sut.getScopeForUnit("unit id", "token", 42);

        assertThat(scopeForUnit, isEmpty());
    }

    @Test
    void tokenNotAuthorized_returnEmptyPoiOptional() {
        when(unitRepository.isTokenAuthorized(any(), any())).thenReturn(false);

        Optional<GetAllPoisResponse> poisForUnit = sut.getPoisForUnit("unit id", "token");

        assertThat(poisForUnit, isEmpty());
    }

    @Test
    void tokenAuthorized_unitUnknown_returnEmptyScopeOptional() {
        String unitId = "unit id";
        String token = "token";
        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);

        Optional<ScopeResponse> scopeForUnit = sut.getScopeForUnit(unitId, token, 42);

        assertThat(scopeForUnit, isEmpty());
    }

    @Test
    void tokenAuthorized_returnScopeResponseWithMappedUnitsAndExistingIncidents() {
        String unitId = "unit id";
        String token = "token";
        ConfiguredUnit ownConfiguredUnit = configuredUnit(
                unitId,
                List.of("ref unit", "missing unit"),
                List.of("incident 1", "missing incident"),
                List.of(new OneTimeAction("type", "https://example.invalid/42", null, "payload")));
        ConfiguredUnit referencedConfiguredUnit = configuredUnit("ref unit", List.of(), List.of(), List.of());

        LiveUnit ownLiveUnit = new LiveUnit("unit id", "Unit", null, null, null, true);
        LiveUnit referencedLiveUnit = new LiveUnit("ref unit", "Ref", null, null, null, false);
        Incident incident = new Incident("incident 1", "type", false, false, "info", null, null, null);
        DispatchIncident dispatchIncident = new DispatchIncident(
                "incident 1",
                "type",
                false,
                false,
                "info",
                null,
                null,
                null,
                Map.of());

        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);
        when(unitRepository.getUnit(unitId)).thenReturn(Optional.of(ownConfiguredUnit));
        when(unitRepository.getUnit("ref unit")).thenReturn(Optional.of(referencedConfiguredUnit));
        var maximumDataAge = 42;
        when(mapper.map(ownConfiguredUnit, maximumDataAge)).thenReturn(ownLiveUnit);
        when(mapper.map(referencedConfiguredUnit, maximumDataAge)).thenReturn(referencedLiveUnit);
        when(incidentRepository.getIncident("incident 1")).thenReturn(Optional.of(incident));
        when(dispatchIncidentMapper.map(anyList(), anyList())).thenReturn(List.of(dispatchIncident));

        Optional<ScopeResponse> scopeForUnit = sut.getScopeForUnit(unitId, token, maximumDataAge);

        ScopeResponse expectedScope = new ScopeResponse(
                List.of(ownLiveUnit, referencedLiveUnit),
                List.of(dispatchIncident),
                ownConfiguredUnit.getAvailableOneTimeActions());
        assertThat(scopeForUnit, isPresentAnd(samePropertyValuesAs(expectedScope)));
    }

    @Test
    void tokenAuthorized_ownUnitIsReferenced_ownUnitAppearsOnlyOnceInScope() {
        String unitId = "unit id";
        String token = "token";
        ConfiguredUnit ownConfiguredUnit = configuredUnit(unitId, List.of(unitId), List.of(), List.of());
        LiveUnit ownLiveUnit = new LiveUnit("unit id", "Unit", null, null, null, true);

        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);
        when(unitRepository.getUnit(unitId)).thenReturn(Optional.of(ownConfiguredUnit));
        when(mapper.map(ownConfiguredUnit, 42)).thenReturn(ownLiveUnit);

        Optional<ScopeResponse> scopeForUnit = sut.getScopeForUnit(unitId, token, 42);

        assertThat(scopeForUnit.isPresent(), is(true));
        assertThat(scopeForUnit.get().getUnits(), equalTo(List.of(ownLiveUnit)));
    }

    @Test
    void tokenAuthorized_returnPois() {
        String unitId = "unit id";
        String token = "token";
        PointOfInterest poi1 = new PointOfInterest("poi-1", "type", "info", null);
        PointOfInterest poi2 = new PointOfInterest("poi-2", "type", "info", null);

        when(unitRepository.isTokenAuthorized(unitId, token)).thenReturn(true);
        when(poiRepository.getAll()).thenReturn(Set.of(poi1, poi2));

        Optional<GetAllPoisResponse> poisForUnit = sut.getPoisForUnit(unitId, token);

        assertThat(poisForUnit.isPresent(), is(true));
        assertThat(poisForUnit.get().getPointsOfInterest(), containsInAnyOrder(poi1, poi2));
    }

    private static ConfiguredUnit configuredUnit(
            final String unitId,
            final List<String> referencedUnits,
            final List<String> referencedIncidents,
            final List<OneTimeAction> oneTimeActions) {
        return ConfiguredUnit.builder(unitId, "Display Name", "token")
                .withUnits(referencedUnits)
                .withIncidents(referencedIncidents)
                .withAvailableOneTimeActions(oneTimeActions)
                .withAvailableForDispatching(true)
                .build();
    }
}

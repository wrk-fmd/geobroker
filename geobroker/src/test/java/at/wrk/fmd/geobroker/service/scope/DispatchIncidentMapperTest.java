/*
 * Copyright (c) 2026 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.generic.Point;
import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.contract.incident.DispatchIncident;
import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class DispatchIncidentMapperTest {
    private DispatchIncidentMapper sut;

    @BeforeEach
    void init() {
        sut = new DispatchIncidentMapper(5);
    }

    @Test
    void map_incidentWithLocation_returnsFiveClosestDispatchableUnitsSortedByDistance() {
        Incident incident = incident("incident-1", new Point(48.2092, 16.3697));
        List<LiveUnit> units = List.of(
                unitWithPosition("unit-6", 48.2504, 16.4349, true),
                unitWithPosition("unit-4", 48.2173, 16.3707, true),
                unitWithPosition("unit-2", 48.2097, 16.3687, true),
                unitWithPosition("unit-5", 48.2226, 16.3755, true),
                unitWithPosition("unit-3", 48.2112, 16.3663, true),
                unitWithPosition("unit-1", 48.2090, 16.3690, true),
                unitWithPosition("blocked", 48.2091, 16.3695, false),
                new LiveUnit("without-point", "without-point", null, null, null, true));

        List<Incident> mappedIncidents = sut.map(List.of(incident), units);

        assertThat(mappedIncidents.get(0), is(instanceOf(DispatchIncident.class)));
        DispatchIncident dispatchIncident = (DispatchIncident) mappedIncidents.get(0);
        Map<String, Integer> unitDistances = dispatchIncident.getAvailableUnitDistancesInMeters();
        assertThat(unitDistances.size(), is(5));
        assertThat(unitDistances.keySet(), contains("unit-1", "unit-2", "unit-3", "unit-4", "unit-5"));
    }

    @Test
    void map_incidentWithoutLocation_returnsEmptyDistanceMap() {
        Incident incident = incident("incident-1", null);

        List<Incident> mappedIncidents = sut.map(List.of(incident), List.of(unitWithPosition("unit-1", 48.2092, 16.3738, true)));

        DispatchIncident dispatchIncident = (DispatchIncident) mappedIncidents.get(0);
        assertThat(dispatchIncident.getAvailableUnitDistancesInMeters(), equalTo(Map.of()));
    }

    @Test
    void map_unitWithoutCurrentPosition_usesLastPointForDistanceCalculation() {
        Incident incident = incident("incident-1", new Point(48.2082, 16.3738));
        LiveUnit unitWithLastPointOnly = new LiveUnit("unit-last-point", "last", new Point(48.2092, 16.3738), null, null, true);

        List<Incident> mappedIncidents = sut.map(List.of(incident), List.of(unitWithLastPointOnly));

        DispatchIncident dispatchIncident = (DispatchIncident) mappedIncidents.get(0);
        assertThat(dispatchIncident.getAvailableUnitDistancesInMeters().size(), is(1));
        assertThat(dispatchIncident.getAvailableUnitDistancesInMeters(), hasKey("unit-last-point"));
    }

    private static Incident incident(final String id, final Point location) {
        return new Incident(id, "type", false, false, "info", location, null, Map.of());
    }

    private static LiveUnit unitWithPosition(final String unitId, final double latitude, final double longitude, final boolean dispatchable) {
        Position position = new Position(latitude, longitude, Instant.EPOCH, null, null, null);
        return new LiveUnit(unitId, unitId, null, null, position, dispatchable);
    }
}

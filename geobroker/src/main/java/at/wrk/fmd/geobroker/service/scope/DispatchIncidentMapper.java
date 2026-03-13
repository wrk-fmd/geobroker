/*
 * Copyright (c) 2026 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.generic.Point;
import at.wrk.fmd.geobroker.contract.incident.DispatchIncident;
import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DispatchIncidentMapper {
    private static final Logger LOG = LoggerFactory.getLogger(DispatchIncidentMapper.class);

    private static final double EARTH_RADIUS_IN_METERS = 6_371_000d;
    private final int maxClosestUnits;

    public DispatchIncidentMapper(@Value("${incident.max.closest.units:5}") final int maxClosestUnits) {
        this.maxClosestUnits = maxClosestUnits;
    }

    public List<Incident> map(final List<Incident> incidents, final List<LiveUnit> units) {
        return incidents.stream()
                .map(incident -> mapToDispatchIncident(incident, units))
                .collect(Collectors.toList());
    }

    private DispatchIncident mapToDispatchIncident(final Incident incident, final List<LiveUnit> units) {
        Map<String, Integer> availableUnitDistancesInMeters = findClosestAvailableUnits(incident, units);
        return new DispatchIncident(
                incident.getId(),
                incident.getType(),
                incident.getPriority(),
                incident.getBlue(),
                incident.getInfo(),
                incident.getLocation(),
                incident.getDestination(),
                incident.getAssignedUnits(),
                availableUnitDistancesInMeters);
    }

    private Map<String, Integer> findClosestAvailableUnits(final Incident incident, final List<LiveUnit> units) {
        if (incident.getLocation() == null) {
            LOG.debug("Incident {} has no location, cannot calculate distances to units", incident.getId());
            return Map.of();
        }

        return getUnitDistanceStream(incident, units)
                .collect(Collectors.toMap(
                        UnitDistance::unitId,
                        UnitDistance::distanceInMeters,
                        (first, second) -> first,
                        LinkedHashMap::new));
    }

    private Stream<UnitDistance> getUnitDistanceStream(final Incident incident, final List<LiveUnit> units) {
        var sortedUnitStream = units.stream()
                .filter(unit -> Boolean.TRUE.equals(unit.getAvailableForDispatching()))
                .map(unit -> unitDistance(incident.getLocation(), unit))
                .filter(Objects::nonNull)
                .sorted(Comparator
                        .comparingInt(UnitDistance::distanceInMeters)
                        .thenComparing(UnitDistance::unitId));
        if (maxClosestUnits > 0) {
            sortedUnitStream = sortedUnitStream.limit(maxClosestUnits);
        }

        return sortedUnitStream;
    }

    private UnitDistance unitDistance(final Point incidentLocation, final LiveUnit unit) {
        Point unitPoint = unit.getCurrentPosition() != null ? unit.getCurrentPosition() : unit.getLastPoint();
        if (unitPoint == null) {
            return null;
        }

        int distanceInMeters = (int) Math.round(calculateDistanceInMeters(incidentLocation, unitPoint));
        return new UnitDistance(unit.getId(), distanceInMeters);
    }

    private double calculateDistanceInMeters(final Point pointA, final Point pointB) {
        double latA = Math.toRadians(pointA.getLatitude());
        double latB = Math.toRadians(pointB.getLatitude());
        double deltaLat = Math.toRadians(pointB.getLatitude() - pointA.getLatitude());
        double deltaLon = Math.toRadians(pointB.getLongitude() - pointA.getLongitude());

        double haversine = calculateHaversine(deltaLat)
                + Math.cos(latA) * Math.cos(latB) * calculateHaversine(deltaLon);
        double angularDistance = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));
        return EARTH_RADIUS_IN_METERS * angularDistance;
    }

    private static double calculateHaversine(final double value) {
        return Math.pow(Math.sin(value / 2), 2);
    }

    private record UnitDistance(String unitId, int distanceInMeters) {
    }
}


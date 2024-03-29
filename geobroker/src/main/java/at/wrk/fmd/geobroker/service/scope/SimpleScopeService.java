/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.contract.poi.GetAllPoisResponse;
import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;
import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import at.wrk.fmd.geobroker.repository.IncidentRepository;
import at.wrk.fmd.geobroker.repository.PoiRepository;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SimpleScopeService implements ScopeService {

    private final UnitRepository unitRepository;
    private final IncidentRepository incidentRepository;
    private final PoiRepository poiRepository;
    private final LiveUnitMapper mapper;

    @Autowired
    public SimpleScopeService(
            final UnitRepository unitRepository,
            final IncidentRepository incidentRepository,
            final PoiRepository poiRepository,
            final LiveUnitMapper mapper) {
        this.unitRepository = unitRepository;
        this.incidentRepository = incidentRepository;
        this.poiRepository = poiRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ScopeResponse> getScopeForUnit(final String unitId, final String token, final int maximumDataAge) {
        Optional<ScopeResponse> response = Optional.empty();
        if (unitRepository.isTokenAuthorized(unitId, token)) {
            Optional<ConfiguredUnit> unit = unitRepository.getUnit(unitId);
            if (unit.isPresent()) {
                ConfiguredUnit ownConfiguredUnit = unit.get();
                LiveUnit ownLiveUnit = mapper.map(ownConfiguredUnit, maximumDataAge);
                List<LiveUnit> referencedLiveUnits = ownConfiguredUnit.getUnits()
                        .stream()
                        .map(unitRepository::getUnit)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(configuredUnit -> mapper.map(configuredUnit, maximumDataAge))
                        .collect(Collectors.toList());
                List<LiveUnit> liveUnits = mergeToList(ownLiveUnit, referencedLiveUnits);

                List<Incident> incidents = ownConfiguredUnit.getIncidents()
                        .stream()
                        .map(incidentRepository::getIncident)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

                response = Optional.of(new ScopeResponse(liveUnits, incidents, ownConfiguredUnit.getAvailableOneTimeActions()));
            }
        }

        return response;
    }

    @Override
    public Optional<GetAllPoisResponse> getPoisForUnit(String unitId, String token) {
        Optional<GetAllPoisResponse> response = Optional.empty();
        if (unitRepository.isTokenAuthorized(unitId, token)) {
            Set<PointOfInterest> allPois = poiRepository.getAll();
            response = Optional.of(new GetAllPoisResponse(allPois));
        }

        return response;
    }

    private static <T> List<T> mergeToList(final T singleObject, final Collection<T> objectCollection) {
        ArrayList<T> liveUnits = new ArrayList<>();
        liveUnits.add(singleObject);
        liveUnits.addAll(objectCollection);
        return liveUnits.stream().distinct().collect(Collectors.toList());
    }
}

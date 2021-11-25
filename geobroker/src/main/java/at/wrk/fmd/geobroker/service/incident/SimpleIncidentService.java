/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.incident;

import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class SimpleIncidentService implements IncidentService {

    private final IncidentRepository incidentRepository;

    @Autowired
    public SimpleIncidentService(final IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Override
    public void createOrUpdateIncident(final Incident incident) {
        Objects.requireNonNull(incident, "Incident to update must not be null");
        incidentRepository.updateIncident(incident);
    }

    @Override
    public void removeIncident(final String incidentId) {
        Objects.requireNonNull(incidentId, "Incident id to remove must not be null!");
        incidentRepository.deleteIncident(incidentId);
    }

    @Override
    public Set<Incident> getAllIncidents() {
        return incidentRepository.getAll();
    }

    @Override
    public Optional<Incident> getIncident(final String incidentId) {
        return incidentRepository.getIncident(incidentId);
    }
}

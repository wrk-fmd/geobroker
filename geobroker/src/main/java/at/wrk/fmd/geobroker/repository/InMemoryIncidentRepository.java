/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.incident.Incident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Scope("singleton")
@ThreadSafe
public class InMemoryIncidentRepository implements IncidentRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryIncidentRepository.class);

    private final Map<String, Incident> storage;

    public InMemoryIncidentRepository() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public void updateIncident(final Incident incident) {
        LOG.trace("Updating incident in storage to {}", incident);
        storage.put(incident.getId(), incident);
    }

    @Override
    public void deleteIncident(final String incidentId) {
        Incident removedIncident = storage.remove(incidentId);

        if (removedIncident != null) {
            LOG.trace("Removed incident from storage: {}", removedIncident);
        } else {
            LOG.trace("Incident with id {} is not present in storage. Nothing to remove.", incidentId);
        }
    }

    @Override
    public Optional<Incident> getIncident(final String incidentId) {
        return Optional.ofNullable(storage.get(incidentId));
    }

    @Override
    public Set<Incident> getAll() {
        return Set.copyOf(storage.values());
    }
}

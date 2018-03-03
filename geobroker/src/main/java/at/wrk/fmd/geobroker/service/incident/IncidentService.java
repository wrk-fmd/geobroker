/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.incident;

import at.wrk.fmd.geobroker.contract.incident.Incident;

import java.util.Optional;
import java.util.Set;

public interface IncidentService {

    void createOrUpdateIncident(Incident incident);

    void removeIncident(String incidentId);

    Set<Incident> getAllIncidents();

    Optional<Incident> getIncident(String incidentId);
}

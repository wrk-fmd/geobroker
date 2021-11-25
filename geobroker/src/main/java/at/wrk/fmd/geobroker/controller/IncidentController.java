/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.incident.GetAllIncidentsResponse;
import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.service.incident.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("${api.url.base}/private/incidents")
public class IncidentController {
    private static final Logger LOG = LoggerFactory.getLogger(IncidentController.class);

    private final IncidentService incidentService;

    @Autowired
    public IncidentController(final IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @RequestMapping(value = "/{incidentId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Incident> updateIncident(
            @PathVariable("incidentId") final String incidentId,
            @RequestBody @Valid final Incident incident) {
        Incident updatedIncident = new Incident(
                incidentId,
                incident.getType(),
                incident.getPriority(),
                incident.getBlue(),
                incident.getInfo(),
                incident.getLocation(),
                incident.getDestination(),
                incident.getAssignedUnits());

        incidentService.createOrUpdateIncident(updatedIncident);
        return ResponseEntity.ok(updatedIncident);
    }

    @RequestMapping(value = "/{incidentId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Serializable> removeIncident(@PathVariable("incidentId") final String incidentId) {
        incidentService.removeIncident(incidentId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{incidentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Incident> getIncident(@PathVariable("incidentId") final String incidentId) {
        ResponseEntity<Incident> entity;
        Optional<Incident> loadedIncident = incidentService.getIncident(incidentId);

        if (loadedIncident.isPresent()) {
            entity = ResponseEntity.ok(loadedIncident.get());
        } else {
            LOG.debug("Requested incident with id {} was not found.", incidentId);
            entity = ResponseEntity.notFound().build();
        }

        return entity;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetAllIncidentsResponse> getAllIncidents() {
        Set<Incident> allIncidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(new GetAllIncidentsResponse(allIncidents));
    }
}

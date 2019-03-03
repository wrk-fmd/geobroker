/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.GetAllUnitsResponse;
import at.wrk.fmd.geobroker.service.unit.UnitService;
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
@RequestMapping("${api.url.base}/private/units")
public class UnitController {
    private static final Logger LOG = LoggerFactory.getLogger(UnitController.class);

    private final UnitService unitService;

    @Autowired
    public UnitController(final UnitService unitService) {
        this.unitService = unitService;
    }

    @RequestMapping(value = "/{unitId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ConfiguredUnit> updateUnit(
            @PathVariable("unitId") final String unitId,
            @RequestBody @Valid final ConfiguredUnit unit) {
        ConfiguredUnit updatedUnit = createConfiguredUnitWithUpdatedId(unitId, unit);
        unitService.createOrUpdateUnit(updatedUnit);
        return ResponseEntity.ok(updatedUnit);
    }

    @RequestMapping(value = "/{unitId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Serializable> removeUnit(@PathVariable("unitId") final String unitId) {
        unitService.removeUnit(unitId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{unitId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ConfiguredUnit> getUnit(@PathVariable("unitId") final String unitId) {
        ResponseEntity<ConfiguredUnit> entity;
        Optional<ConfiguredUnit> loadedUnit = unitService.getUnit(unitId);

        if (loadedUnit.isPresent()) {
            entity = ResponseEntity.ok(loadedUnit.get());
        } else {
            LOG.debug("Requested unit with id {} was not found.", unitId);
            entity = ResponseEntity.notFound().build();
        }

        return entity;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetAllUnitsResponse> getAllUnits() {
        Set<ConfiguredUnit> allUnits = unitService.getAllUnits();
        return ResponseEntity.ok(new GetAllUnitsResponse(allUnits));
    }

    private ConfiguredUnit createConfiguredUnitWithUpdatedId(final String updatedUnitId, final ConfiguredUnit unit) {
        return new ConfiguredUnit(
                updatedUnitId,
                unit.getName(),
                unit.getToken(),
                unit.getUnits(),
                unit.getIncidents(),
                unit.getLastPoint(),
                unit.getTargetPoint(),
                unit.getAvailableForDispatching());
    }
}

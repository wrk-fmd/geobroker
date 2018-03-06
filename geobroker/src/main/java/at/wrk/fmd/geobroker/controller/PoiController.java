/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.poi.GetAllPoisResponse;
import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;
import at.wrk.fmd.geobroker.service.poi.PoiService;
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
@RequestMapping("${api.url.base}/private/pois")
public class PoiController {
    private static final Logger LOG = LoggerFactory.getLogger(PoiController.class);

    private final PoiService poiService;

    @Autowired
    public PoiController(final PoiService poiService) {
        this.poiService = poiService;
    }

    @RequestMapping(value = "/{poiId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PointOfInterest> updatePoi(
            @PathVariable("poiId") final String poiId,
            @RequestBody @Valid final PointOfInterest poi) {
        PointOfInterest updatedPoi = new PointOfInterest(
                poiId,
                poi.getType(),
                poi.getInfo(),
                poi.getLocation());

        poiService.createOrUpdatePoi(updatedPoi);
        return ResponseEntity.ok(updatedPoi);
    }

    @RequestMapping(value = "/{poiId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Serializable> removePoi(@PathVariable("poiId") final String poiId) {
        poiService.removePoi(poiId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{poiId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PointOfInterest> getPoi(@PathVariable("poiId") final String poiId) {
        ResponseEntity<PointOfInterest> entity;
        Optional<PointOfInterest> loadedPoi = poiService.getPoi(poiId);

        if (loadedPoi.isPresent()) {
            entity = ResponseEntity.ok(loadedPoi.get());
        } else {
            LOG.debug("Requested POI with id {} was not found.", poiId);
            entity = ResponseEntity.notFound().build();
        }

        return entity;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetAllPoisResponse> getAllPois() {
        Set<PointOfInterest> allPois = poiService.getAllPois();
        return ResponseEntity.ok(new GetAllPoisResponse(allPois));
    }
}

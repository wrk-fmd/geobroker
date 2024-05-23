/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.service.position.PositionService;
import at.wrk.fmd.geobroker.service.position.PositionUpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

@Controller
@RequestMapping("${api.url.base}/public/positions")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(final PositionService positionService) {
        this.positionService = positionService;
    }


    @RequestMapping(value = "/{unitId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Position> getPosition(
            final @PathVariable("unitId") String unitId,
            final @RequestParam("token") String token,
            final @RequestBody @Valid Position updatedPosition) {
        ResponseEntity<Position> response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        PositionUpdateResult result = positionService.updatePosition(unitId, token, updatedPosition);
        if (result == PositionUpdateResult.UPDATED) {
            response = ResponseEntity.ok(updatedPosition);
        }

        return response;
    }
}

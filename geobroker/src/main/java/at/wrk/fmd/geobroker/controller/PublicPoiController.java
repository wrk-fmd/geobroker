/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.poi.GetAllPoisResponse;
import at.wrk.fmd.geobroker.service.scope.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("${api.url.base}/public/poi")
public class PublicPoiController {

    private final ScopeService scopeService;

    @Autowired
    public PublicPoiController(final ScopeService scopeService) {this.scopeService = scopeService;}

    @RequestMapping(value = "/{unitId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetAllPoisResponse> getPois(
            final @PathVariable("unitId") String unitId,
            final @RequestParam("token") String token) {
        ResponseEntity<GetAllPoisResponse> response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Optional<GetAllPoisResponse> poisForUnit = scopeService.getPoisForUnit(unitId, token);
        if (poisForUnit.isPresent()) {
            response = ResponseEntity.ok(poisForUnit.get());
        }

        return response;
    }
}

/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.service.scope.ScopeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("${api.url.base}/public/scope")
public class ScopeController {
    private static final Logger LOG = LoggerFactory.getLogger(ScopeController.class);

    private final ScopeService scopeService;
    private final int defaultMaxDataAge;

    @Autowired
    public ScopeController(
            final ScopeService scopeService,
            @Value("${data.age.default.minutes:10}") final int defaultMaxDataAge) {
        this.scopeService = scopeService;
        this.defaultMaxDataAge = defaultMaxDataAge;
    }

    @RequestMapping(value = "/{unitId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ScopeResponse> getScope(
            final @PathVariable("unitId") String unitId,
            final @RequestParam("token") String token,
            final @RequestParam(value = "maxDataAge", required = false, defaultValue = "") String maximumDataAge) {
        ResponseEntity<ScopeResponse> response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        int effectiveMaximumDataAge = getEffectiveMaximumDataAge(maximumDataAge);
        Optional<ScopeResponse> scopeForUnit = scopeService.getScopeForUnit(unitId, token, effectiveMaximumDataAge);
        if (scopeForUnit.isPresent()) {
            response = ResponseEntity.ok(scopeForUnit.get());
        }

        return response;
    }

    private int getEffectiveMaximumDataAge(final String maximumDataAge) {
        int effectiveMaximumDataAge = this.defaultMaxDataAge;
        if (StringUtils.isNumeric(maximumDataAge)) {
            try {
                effectiveMaximumDataAge = Integer.parseInt(maximumDataAge);
            } catch (NumberFormatException e) {
                LOG.debug("Provided invalid maxDataAge parameter: {}", maximumDataAge);
            }
        }

        return effectiveMaximumDataAge;
    }
}

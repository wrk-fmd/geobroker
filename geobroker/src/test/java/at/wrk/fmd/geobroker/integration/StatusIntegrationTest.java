/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.integration;

import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.contract.status.StatusResponse;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.GetAllUnitsResponse;
import at.wrk.fmd.geobroker.util.Positions;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static at.wrk.fmd.geobroker.integration.UrlHelper.privateApiUrl;
import static at.wrk.fmd.geobroker.integration.UrlHelper.publicApiUrl;
import static at.wrk.fmd.geobroker.util.ConfiguredUnits.randomUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class StatusIntegrationTest extends AbstractRestIntegrationTest {

    @Test
    public void createdUnitCanBeRetrieved() {
        StatusResponse statusResponse = restTemplate.getForObject(privateApiUrl("/status"), StatusResponse.class);
        assertThat(statusResponse.getInstanceId(), notNullValue());
    }
}

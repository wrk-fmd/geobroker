/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.integration;

import at.wrk.fmd.geobroker.contract.status.StatusResponse;
import org.junit.jupiter.api.Test;

import static at.wrk.fmd.geobroker.integration.UrlHelper.privateApiUrl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class StatusIntegrationTest extends AbstractRestIntegrationTest {

    @Test
    void createdUnitCanBeRetrieved() {
        StatusResponse statusResponse = restTemplate.getForObject(privateApiUrl("/status"), StatusResponse.class);
        assertThat(statusResponse.getInstanceId(), notNullValue());
    }
}

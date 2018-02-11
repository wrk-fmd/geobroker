/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.integration;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.GetAllUnitsResponse;
import at.wrk.fmd.geobroker.startup.GeoBrokerBootstrapper;
import at.wrk.fmd.geobroker.startup.GeobrokerPropertyConfiguration;
import at.wrk.fmd.geobroker.startup.WebConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static at.wrk.fmd.geobroker.util.ConfiguredUnits.createUnit;
import static at.wrk.fmd.geobroker.integration.UrlHelper.privateApiUrl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {GeoBrokerBootstrapper.class, WebConfiguration.class, GeobrokerPropertyConfiguration.class})
public class UnitIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createdUnitCanBeRetrieved() {
        String unitId = "test-unit-id";
        ConfiguredUnit configuredUnit = createUnit(unitId, "Unit Token");
        restTemplate.put(privateApiUrl("/units/" + unitId), configuredUnit);

        ConfiguredUnit storedUnit = restTemplate.getForObject(privateApiUrl("/units/test-unit-id"), ConfiguredUnit.class);
        assertThat(storedUnit, samePropertyValuesAs(configuredUnit));
    }

    @Test
    public void createdUnitIsInUnitList() {
        String unitId = "test-unit-id";
        ConfiguredUnit configuredUnit = createUnit(unitId, "token");
        restTemplate.put(privateApiUrl("/units/" + unitId), configuredUnit);

        GetAllUnitsResponse response = restTemplate.getForObject(privateApiUrl("/units"), GetAllUnitsResponse.class);
        assertThat(response.getConfiguredUnits(), contains(samePropertyValuesAs(configuredUnit)));
    }
}

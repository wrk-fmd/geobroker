/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.integration;

import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.GetAllUnitsResponse;
import at.wrk.fmd.geobroker.startup.GeoBrokerBootstrapper;
import at.wrk.fmd.geobroker.startup.GeobrokerPropertyConfiguration;
import at.wrk.fmd.geobroker.startup.WebConfiguration;
import at.wrk.fmd.geobroker.util.Positions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static at.wrk.fmd.geobroker.integration.UrlHelper.privateApiUrl;
import static at.wrk.fmd.geobroker.integration.UrlHelper.publicApiUrl;
import static at.wrk.fmd.geobroker.util.ConfiguredUnits.randomUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {GeoBrokerBootstrapper.class, WebConfiguration.class, GeobrokerPropertyConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UnitIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createdUnitCanBeRetrieved() {
        String unitId = "test-unit-id";
        ConfiguredUnit configuredUnit = createConfiguredUnit(unitId, "any token");

        ConfiguredUnit storedUnit = restTemplate.getForObject(privateApiUrl("/units/test-unit-id"), ConfiguredUnit.class);
        assertThat(storedUnit, samePropertyValuesAs(configuredUnit));
    }

    @Test
    public void createdUnitIsInUnitList() {
        String unitId = "test-unit-id";
        ConfiguredUnit configuredUnit = createConfiguredUnit(unitId, "any token");

        GetAllUnitsResponse response = restTemplate.getForObject(privateApiUrl("/units"), GetAllUnitsResponse.class);
        assertThat(response.getConfiguredUnits(), contains(samePropertyValuesAs(configuredUnit)));
    }

    @Ignore("Spring boot fails to serialize Instants in the test code...")
    @Test
    public void unitRequestsScope_unitHasOtherUnitsAssigned_unitRetrievesPositionUpdates() {
        String unitId = "test-unit-0";
        String referencedUnitId1 = "referenced-unit-1";
        String referencedUnitId2 = "referenced-unit-2";
        String token = "access token";

        createConfiguredUnit(referencedUnitId1, token);
        createConfiguredUnit(referencedUnitId2, token);

        ConfiguredUnit configuredUnit = randomUnit(unitId, token, ImmutableList.of(referencedUnitId1, referencedUnitId2));
        restTemplate.put(privateApiUrl("/units/" + unitId), configuredUnit);

        postPositionUpdate(unitId, token);
        postPositionUpdate(referencedUnitId1, token);
        postPositionUpdate(referencedUnitId2, token);

        ScopeResponse response = restTemplate.getForObject(publicApiUrl("/scope/" + unitId), ScopeResponse.class, ImmutableMap.of("token", token));
        assertThat(response.getUnits(), hasSize(3));
        response.getUnits().forEach(liveUnit -> assertThat("Valid Position shall be set for unit", liveUnit.getCurrentPosition(), notNullValue()));
    }

    private void postPositionUpdate(final String unitId, final String token) {
        restTemplate.postForObject(publicApiUrl("/positions/" + unitId), Positions.random(), Position.class, ImmutableMap.of("token", token));
    }

    private ConfiguredUnit createConfiguredUnit(final String unitId, final String token) {
        ConfiguredUnit configuredUnit = randomUnit(unitId, token);
        restTemplate.put(privateApiUrl("/units/" + unitId), configuredUnit);
        return configuredUnit;
    }
}

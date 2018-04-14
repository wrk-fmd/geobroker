/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.integration;

import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.startup.GeoBrokerBootstrapper;
import at.wrk.fmd.geobroker.startup.GeobrokerPropertyConfiguration;
import at.wrk.fmd.geobroker.startup.WebConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static at.wrk.fmd.geobroker.integration.UrlHelper.privateApiUrl;
import static at.wrk.fmd.geobroker.integration.UrlHelper.publicApiUrl;
import static at.wrk.fmd.geobroker.util.ConfiguredUnits.randomUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {GeoBrokerBootstrapper.class, WebConfiguration.class, GeobrokerPropertyConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractRestIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    protected ScopeResponse getScopeForUnit(final String unitId, final String token) {
        return restTemplate.getForObject(publicApiUrl("/scope/" + unitId + "?token=" + token), ScopeResponse.class);
    }

    protected void postPositionUpdate(final String unitId, final String token, final Position position) {
        restTemplate.postForEntity(publicApiUrl("/positions/" + unitId + "?token=" + token), position, Position.class);
    }

    protected ConfiguredUnit createConfiguredUnit(final String unitId, final String token) {
        ConfiguredUnit configuredUnit = randomUnit(unitId, token);
        restTemplate.put(privateApiUrl("/units/" + unitId), configuredUnit);
        return configuredUnit;
    }
}

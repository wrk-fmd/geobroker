/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.integration;

import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.GetAllUnitsResponse;
import at.wrk.fmd.geobroker.util.Positions;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
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

class UnitIntegrationTest extends AbstractRestIntegrationTest {

    @Test
    void createdUnitCanBeRetrieved() {
        String unitId = "test-unit-id";
        ConfiguredUnit configuredUnit = createConfiguredUnit(unitId, "any token");

        ConfiguredUnit storedUnit = restTemplate.getForObject(privateApiUrl("/units/test-unit-id"), ConfiguredUnit.class);
        assertThat(storedUnit, samePropertyValuesAs(configuredUnit));
    }

    @Test
    void createdUnitIsInUnitList() {
        String unitId = "test-unit-id";
        ConfiguredUnit configuredUnit = createConfiguredUnit(unitId, "any token");

        GetAllUnitsResponse response = restTemplate.getForObject(privateApiUrl("/units"), GetAllUnitsResponse.class);
        assertThat(response.getConfiguredUnits(), contains(samePropertyValuesAs(configuredUnit)));
    }

    @Test
    void unitRequestsScope_unitHasOtherUnitsAssigned_unitRetrievesPositionUpdates() {
        String unitId = "test-unit-0";
        String referencedUnitId1 = "referenced-unit-1";
        String referencedUnitId2 = "referenced-unit-2";
        String token = "access token";

        createConfiguredUnit(referencedUnitId1, token);
        createConfiguredUnit(referencedUnitId2, token);

        ConfiguredUnit configuredUnit = randomUnit(unitId, token, ImmutableList.of(referencedUnitId1, referencedUnitId2));
        restTemplate.put(privateApiUrl("/units/" + unitId), configuredUnit);

        postPositionUpdate(unitId, token, Positions.random());
        postPositionUpdate(referencedUnitId1, token, Positions.random());
        postPositionUpdate(referencedUnitId2, token, Positions.random());

        ScopeResponse response = getScopeForUnit(unitId, token);
        assertThat(response.getUnits(), hasSize(3));
        response.getUnits().forEach(liveUnit -> assertThat("Valid Position shall be set for unit", liveUnit.getCurrentPosition(), notNullValue()));
    }

    @Test
    void unitsSetPositionWithoutValues_respondWithError() {

        String token = "test token";
        String unitId = "unit-id";
        createConfiguredUnit(unitId, token);

        FakePosition invalidPosition = createInvalidPosition();
        ResponseEntity<String> response = restTemplate.postForEntity(publicApiUrl("/positions/" + unitId + "?token=" + token), invalidPosition, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    void unitRequestsScope_unitHasOneTimeAction_unitRetrievesAction() {
        String unitId = "test-unit-0";
        String token = "access token";

        ConfiguredUnit configuredUnit = randomUnit(unitId, token, ImmutableList.of());
        restTemplate.put(privateApiUrl("/units/" + unitId), configuredUnit);

        ScopeResponse response = getScopeForUnit(unitId, token);
        assertThat(response.getAvailableOneTimeActions(), equalTo(configuredUnit.getAvailableOneTimeActions()));
    }

    private FakePosition createInvalidPosition() {
        return new FakePosition(RandomUtils.nextDouble(5, 20), Instant.now());
    }

    private static class FakePosition {
        private final double latitude;
        private final Instant timestamp;

        private FakePosition(final double latitude, final Instant timestamp) {
            this.latitude = latitude;
            this.timestamp = timestamp;
        }

        public double getLatitude() {
            return latitude;
        }

        public Instant getTimestamp() {
            return timestamp;
        }
    }
}

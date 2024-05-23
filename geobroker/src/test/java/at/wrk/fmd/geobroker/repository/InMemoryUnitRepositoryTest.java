/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static at.wrk.fmd.geobroker.util.ConfiguredUnits.randomUnit;
import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class InMemoryUnitRepositoryTest {
    private UnitRepository sut;

    @BeforeEach
    void init() {
        sut = new InMemoryUnitRepository();
    }

    @Test
    void storeUnit_unitCanBeRetrieved() {
        String unitId = "unit-id";
        ConfiguredUnit unit = randomUnit(unitId, "token");
        sut.updateUnit(unit);

        Optional<ConfiguredUnit> retrievedUnit = sut.getUnit(unitId);
        assertThat(retrievedUnit, hasValue(samePropertyValuesAs(unit)));
    }

    @Test
    void storeUnit_validTokenIsAuthorized() {
        String unitId = "unit-id";
        String token = "token";
        ConfiguredUnit unit = randomUnit(unitId, token);
        sut.updateUnit(unit);

        boolean isAuthorized = sut.isTokenAuthorized(unitId, token);
        assertThat(isAuthorized, is(true));
    }

    @Test
    void storeUnit_invalidTokenIsNotAuthorized() {
        String unitId = "unit-id";
        String token = "token";
        ConfiguredUnit unit = randomUnit(unitId, token);
        sut.updateUnit(unit);

        boolean isAuthorized = sut.isTokenAuthorized(unitId, "another token");
        assertThat(isAuthorized, is(false));
    }

    @Test
    void deleteStoredUnit_unitCannotBeRetrieved() {
        String unitId = "unit-id";
        ConfiguredUnit unit = randomUnit(unitId, "token");
        sut.updateUnit(unit);
        sut.deleteUnit(unitId);

        Optional<ConfiguredUnit> retrievedUnit = sut.getUnit(unitId);
        assertThat(retrievedUnit, isEmpty());
    }
}

/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.util;

import at.wrk.fmd.geobroker.contract.generic.OneTimeAction;
import at.wrk.fmd.geobroker.contract.generic.Point;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public final class ConfiguredUnits {
    private ConfiguredUnits() {
    }

    public static ConfiguredUnit randomUnit(final String unitId, final String token) {
        return randomUnit(
                unitId,
                token,
                ImmutableList.of("referenced unit 1 " + randomAlphabetic(), "referenced unit 2 " + randomAlphabetic()));
    }

    public static ConfiguredUnit randomUnit(final String unitId, final String token, final List<String> referencedUnitIds) {
        return ConfiguredUnit.builder(unitId, "Display Name " + randomAlphabetic(), token)
                .withUnits(referencedUnitIds)
                .withIncidents(ImmutableList.of("referenced incident 1 " + randomAlphabetic(), "referenced incident 2 " + randomAlphabetic()))
                .withLastPoint(new Point(123d, RandomUtils.nextDouble(0, 90)))
                .withTargetPoint(new Point(938d, RandomUtils.nextDouble(0, 90)))
                .withAvailableForDispatching(RandomUtils.nextBoolean())
                .withAvailableOneTimeActions(ImmutableList.of(randomOneTimeAction()))
                .build();
    }

    private static OneTimeAction randomOneTimeAction() {
        return new OneTimeAction(
                "integrationTestOTA",
                "https://invalid.local/integrationTest/42",
                null,
                RandomStringUtils.randomAlphabetic(10));
    }

    private static String randomAlphabetic() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}

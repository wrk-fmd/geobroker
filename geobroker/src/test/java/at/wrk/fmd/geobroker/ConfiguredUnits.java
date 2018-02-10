/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker;

import at.wrk.fmd.geobroker.contract.generic.Point;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import com.google.common.collect.ImmutableList;

public final class ConfiguredUnits {
    private ConfiguredUnits() {
    }

    public static ConfiguredUnit createUnit(final String unitId, final String token) {
        return new ConfiguredUnit(
                unitId,
                "Display Name",
                token,
                ImmutableList.of("referenced unit 1", "referenced unit 2"),
                ImmutableList.of("referenced incident 1", "referenced incident 2"),
                new Point(123, 456),
                new Point(938, 293)
        );
    }
}

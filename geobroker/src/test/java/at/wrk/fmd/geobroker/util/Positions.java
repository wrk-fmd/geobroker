/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.util;

import at.wrk.fmd.geobroker.contract.generic.Position;

import java.time.Instant;

public final class Positions {
    private Positions() {
    }

    public static Position createPosition() {
        return createPosition(Instant.now());
    }

    public static Position createPosition(final Instant timestamp) {
        return new Position(12.3, 11.4, timestamp, 3.4, 10.0, 1.3);
    }
}

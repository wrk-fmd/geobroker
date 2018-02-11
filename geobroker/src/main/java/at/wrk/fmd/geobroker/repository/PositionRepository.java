/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.generic.Position;

import java.util.Optional;

public interface PositionRepository {
    void storePosition(String unitId, Position lastPosition);

    Optional<Position> getPosition(String unitId);
}

/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.position;

import at.wrk.fmd.geobroker.contract.generic.Position;

public interface PositionService {

    PositionUpdateResult updatePosition(String unitId, String token, Position updatedPosition);
}

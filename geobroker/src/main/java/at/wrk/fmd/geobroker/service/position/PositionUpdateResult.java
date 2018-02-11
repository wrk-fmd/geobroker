/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.position;

public enum PositionUpdateResult {
    /**
     * The given position was successfully updated.
     */
    UPDATED,

    /**
     * The unit identifier and token combination is invalid.
     */
    NOT_ALLOWED,
}

/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;

import java.util.Optional;

public interface ScopeService {
    /**
     * Returns scope of the given unit. If the token is not valid, an empty optional is returned.
     */
    Optional<ScopeResponse> getScopeForUnit(String unitId, String token);
}

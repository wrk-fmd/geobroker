/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;

import java.util.Optional;
import java.util.Set;

public interface UnitRepository {

    boolean isTokenAuthorized(final String unitId, final String token);

    void updateUnit(final ConfiguredUnit unit);

    void deleteUnit(final String unitId);

    Optional<ConfiguredUnit> getUnit(final String unitId);

    /**
     * Returns a set of unit IDs of the units which have access to the unit with ID <code>unitIdToCheck</code>.
     */
    Set<String> getUnitsWithAccessTo(final String unitIdToCheck);

    Set<ConfiguredUnit> getAll();
}

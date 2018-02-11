/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.unit;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;

import java.util.Optional;
import java.util.Set;

public interface UnitService {

    void createOrUpdateUnit(ConfiguredUnit unit);

    void removeUnit(String unitId);

    Set<ConfiguredUnit> getAllUnits();

    Optional<ConfiguredUnit> getUnit(String unitId);
}

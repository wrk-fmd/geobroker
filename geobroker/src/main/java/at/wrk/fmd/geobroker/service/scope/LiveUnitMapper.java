/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import at.wrk.fmd.geobroker.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LiveUnitMapper {

    private final PositionRepository positionRepository;

    @Autowired
    public LiveUnitMapper(final PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public LiveUnit map(final ConfiguredUnit unit, final int maximumDataAge) {
        Optional<Position> position = positionRepository.getPosition(unit.getId(), maximumDataAge);
        return new LiveUnit(unit.getId(), unit.getName(), unit.getLastPoint(), unit.getTargetPoint(), position.orElse(null));
    }
}

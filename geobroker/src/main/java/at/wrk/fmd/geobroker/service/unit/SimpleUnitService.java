/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.unit;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import at.wrk.fmd.geobroker.service.event.UnitUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class SimpleUnitService implements UnitService {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUnitService.class);

    private final UnitRepository unitRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public SimpleUnitService(final UnitRepository unitRepository, final ApplicationEventPublisher applicationEventPublisher) {
        this.unitRepository = unitRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void createOrUpdateUnit(final ConfiguredUnit unit) {
        Objects.requireNonNull(unit, "Unit to update must not be null");
        unitRepository.updateUnit(unit);
        applicationEventPublisher.publishEvent(new UnitUpdatedEvent(unit.getId()));
        LOG.info("#unitupdate Unit was updated successfully. unitId: '{}', name: '{}'", unit.getId(), unit.getName());
    }

    @Override
    public void removeUnit(final String unitId) {
        Objects.requireNonNull(unitId, "Unit id to remove must not be null!");
        unitRepository.deleteUnit(unitId);
        // TODO raise unit deleted event
    }

    @Override
    public Set<ConfiguredUnit> getAllUnits() {
        return unitRepository.getAll();
    }

    @Override
    public Optional<ConfiguredUnit> getUnit(final String unitId) {
        return unitRepository.getUnit(unitId);
    }
}
